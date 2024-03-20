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
            val response = profileRepository.getProfile(userId)
            if (response.status == HttpStatusCode.OK) {
                val profile = response.body<Profile>()
                reduce { state.copy(profile = profile) }
            } else {
                postSideEffect(ProfileSideEffect.Error(response.status.description))
            }
        } else {
            postSideEffect(ProfileSideEffect.NotAuthorized)
        }
    }

    fun updateProfile(name: String, photo: String, inputStream: InputStream?, fileName: String?) = intent {
        val userId = authRepository.getUserId()
        if (userId != -1L) {
            state.profile?.let { profile ->
                var fileId = profile.file?.id
                if (inputStream != null && fileName != null) {
                    val response = profileRepository.uploadFile(
                        inputStream.readBytes(),
                        fileName,
                        authRepository.getAuthToken()
                    )
                    fileId = response.body<Long>()
                }
                val response = profileRepository.updateProfile(
                    profile.copy(
                        name = name,
                        photo = photo,
                        file = fileId?.let { id -> profile.file?.copy(id = id) }
                    )
                )
                if (response.status != HttpStatusCode.OK) {
                    postSideEffect(ProfileSideEffect.Error(response.status.description))
                }
            }
        } else {
            postSideEffect(ProfileSideEffect.NotAuthorized)
        }
    }
}

data class ProfileState(val profile: Profile? = null)

sealed class ProfileSideEffect {
    data object NotAuthorized : ProfileSideEffect()

    data class Error(val message: String) : ProfileSideEffect()
}