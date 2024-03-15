package com.example.profquest2.ui.screens.profile.auth.signup

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
class CodeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ContainerHost<CodeState, CodeSideEffect>, ViewModel() {

    override val container: Container<CodeState, CodeSideEffect> = container(CodeState())

    fun signUp(email: String, password: String, code: String) = intent {
        val response = authRepository.signUp(email, password, code)
        postSideEffect(
            if (response.status == HttpStatusCode.Created) {
                val signInResponse = authRepository.signIn(email, password)
                if (signInResponse.status == HttpStatusCode.OK) {
                    CodeSideEffect.Success
                } else {
                    CodeSideEffect.Error(signInResponse.status.description)
                }
            } else {
                CodeSideEffect.Error(response.status.description)
            }
        )
    }

    fun sendCode(email: String) = intent {
        val response = authRepository.sendCode(email)
        postSideEffect(
            if (response.status == HttpStatusCode.OK) {
                CodeSideEffect.CodeResend
            } else {
                CodeSideEffect.Error(response.status.description)
            }
        )
    }
}

class CodeState

sealed class CodeSideEffect {
    data object Success : CodeSideEffect()

    data object CodeResend: CodeSideEffect()

    data class Error(val message: String) : CodeSideEffect()
}