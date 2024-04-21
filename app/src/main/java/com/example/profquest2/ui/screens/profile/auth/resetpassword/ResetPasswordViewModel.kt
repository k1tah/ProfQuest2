package com.example.profquest2.ui.screens.profile.auth.resetpassword

import androidx.lifecycle.ViewModel
import com.example.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.http.HttpStatusCode
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ContainerHost<ResetPasswordState, ResetPasswordSideEffect>, ViewModel() {

    override val container: Container<ResetPasswordState, ResetPasswordSideEffect> =
        container(ResetPasswordState())

    fun sendCode(email: String) = intent {
        val response = authRepository.sendCode(email)
        postSideEffect(
            if (response.status == HttpStatusCode.OK) {
                ResetPasswordSideEffect.SuccessCodeSend
            } else {
                ResetPasswordSideEffect.Error(response.status.description)
            }
        )
    }

    fun resetPassword(email: String, code: String, password: String) = intent {
        val response = authRepository.resetPassword(email, code, password)
        postSideEffect(
            if (response.status == HttpStatusCode.OK) {
                ResetPasswordSideEffect.SuccessReset
            } else {
                ResetPasswordSideEffect.Error(response.status.description)
            }
        )
    }
}

class ResetPasswordState

sealed class ResetPasswordSideEffect {
    data object SuccessReset : ResetPasswordSideEffect()

    data object SuccessCodeSend : ResetPasswordSideEffect()

    data class Error(val message: String) : ResetPasswordSideEffect()
}