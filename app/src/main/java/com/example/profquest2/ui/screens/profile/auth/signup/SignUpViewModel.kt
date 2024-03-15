package com.example.profquest2.ui.screens.profile.auth.signup

import androidx.lifecycle.ViewModel
import com.example.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.http.HttpStatusCode
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ContainerHost<SignUpState, SignUpSideEffect>, ViewModel() {

    override val container: Container<SignUpState, SignUpSideEffect> = container(SignUpState())

    fun sendCode(email: String) = intent {
        val response = authRepository.sendCode(email)
        postSideEffect(
            if (response.status == HttpStatusCode.OK) {
                SignUpSideEffect.Success
            } else {
                SignUpSideEffect.Error(response.status.description)
            }
        )
    }
}

class SignUpState

sealed class SignUpSideEffect {
    data object Success : SignUpSideEffect()

    data class Error(val message: String) : SignUpSideEffect()
}