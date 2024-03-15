package com.example.profquest2.ui.screens.profile.auth.signin

import androidx.lifecycle.ViewModel
import com.example.data.api.body.AuthResponseBody
import com.example.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ContainerHost<SignInState, SignInSideEffect>, ViewModel() {

    override val container: Container<SignInState, SignInSideEffect> =
        container(SignInState())

    fun signIn(login: String, password: String) = intent {
        val response = authRepository.signIn(login, password)
        postSideEffect(
            if (response.status == HttpStatusCode.OK) {
                SignInSideEffect.Success
            } else {
                SignInSideEffect.Error(response.status.description)
            }
        )
    }
}

class SignInState

sealed class SignInSideEffect {
    data object Success : SignInSideEffect()

    data class Error(val message: String) : SignInSideEffect()
}