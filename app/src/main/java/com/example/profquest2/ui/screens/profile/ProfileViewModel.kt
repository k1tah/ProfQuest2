package com.example.profquest2.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.data.repository.AuthRepository
import com.example.data.repository.ProfileRepository
import com.example.domain.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) : ContainerHost<ProfileState, ProfileSideEffect>, ViewModel() {

    override val container: Container<ProfileState, ProfileSideEffect> = container(ProfileState())

    init {
        getProfile()
    }

    private fun getProfile() = intent {
        val userId = authRepository.getUserId()
        if (userId != -1L) {
            val response = profileRepository.getProfile(authRepository.getAuthToken())
            if (response.status == HttpStatusCode.OK) {
                val profile = response.body<Profile>()
                reduce { state.copy(profile = profile) }
                postSideEffect(ProfileSideEffect.Done)
            } else {
                postSideEffect(ProfileSideEffect.Error(response.status.value.toString()))
            }
        } else {
            postSideEffect(ProfileSideEffect.NotAuthorized)
        }
    }

    fun updateProfile(
        name: String,
        fileInputStream: InputStream?,
        fileName: String?,
        photoInputStream: InputStream?
    ) = intent {
        postSideEffect(ProfileSideEffect.Loading)
        val userId = authRepository.getUserId()
        if (userId != -1L) {
            var fileId = state.profile?.file?.id
            var photoId = state.profile?.photo?.id
            if (fileInputStream != null && fileName != null) {
                val response = profileRepository.uploadFile(
                    fileInputStream.readBytes(),
                    fileName,
                    authRepository.getAuthToken()
                )
                fileId = response.body<Long>()
            }
            if (photoInputStream != null) {
                val response = profileRepository.uploadFile(
                    photoInputStream.readBytes(),
                    token = authRepository.getAuthToken()
                )
                photoId = response.body<Long>()
            }
            val response = profileRepository.updateProfile(name, photoId, fileId, authRepository.getAuthToken())
            if (response.status == HttpStatusCode.OK) {
                getProfile()
            } else {
                postSideEffect(ProfileSideEffect.Error(response.status.description))
            }
        } else {
            postSideEffect(ProfileSideEffect.NotAuthorized)
        }
    }
}

data class ProfileState(val profile: Profile? = null)

sealed class ProfileSideEffect {
    data object Loading : ProfileSideEffect()

    data object NotAuthorized : ProfileSideEffect()

    data class Error(val message: String) : ProfileSideEffect()

    data object Done : ProfileSideEffect()
}