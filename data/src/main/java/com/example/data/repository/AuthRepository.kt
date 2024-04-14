package com.example.data.repository

import com.example.data.api.ApiService
import com.example.data.api.body.auth.AuthResponseBody
import com.example.data.store.AuthStore
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val authStore: AuthStore
) {
    suspend fun signIn(login: String, password: String): HttpResponse {
        val response = apiService.signIn(login, password)
        if (response.status == HttpStatusCode.OK) {
            val userId = (response.body() as AuthResponseBody).id
            with(authStore) {
                setUserId(userId)
                setLogin(login)
                setPassword(password)
            }
        }
        return response
    }

    suspend fun signUp(email: String, password: String, code: String) =
        apiService.signUp(email, password, code)

    suspend fun sendCode(email: String) = apiService.sendCode(email)

    suspend fun resetPassword(email: String, code: String, password: String) = apiService.resetPassword(email, code, password)

    fun getAuthToken() = authStore.getAuthToken()

    fun getUserId() = authStore.getUserId()
}