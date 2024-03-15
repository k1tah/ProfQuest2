package com.example.data.api

import com.example.data.api.body.AuthRequestBody
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

class ApiService {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) { json() }
        install(HttpTimeout) {
            requestTimeoutMillis = 100000
            socketTimeoutMillis = 100000
            connectTimeoutMillis = 100000
        }
    }

    suspend fun signIn(login: String, password: String) = client.post(BASE_URL + "user/signin") {
        contentType(ContentType.Application.Json)
        setBody(AuthRequestBody(login, password))
    }

    suspend fun signUp(email: String, password: String, code: String) = client.post(BASE_URL + "user/registration") {
        contentType(ContentType.Application.Json)
        setBody(AuthRequestBody(email, password))
        url {
            parameters.append("email", email)
            parameters.append("token", code)
        }
    }

    suspend fun sendCode(email: String) = client.post(BASE_URL + "user/code") {
        url { parameters.append("email", email) }
    }

    companion object {
        const val BASE_URL = "http://192.168.91.131:8080/"
    }
}