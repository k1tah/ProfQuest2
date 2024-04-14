package com.example.data.api

import android.util.Log
import com.example.data.api.body.UpdateProfileRequestBody
import com.example.data.api.body.auth.AuthRequestBody
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
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
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("TAG", "Ktor: $message \n ------------------------------ \n")
                }
            }
            level = LogLevel.BODY
        }
    }

    suspend fun signIn(login: String, password: String) = client.post(BASE_URL + "user/signin") {
        contentType(ContentType.Application.Json)
        setBody(AuthRequestBody(login, password))
    }

    suspend fun signUp(email: String, password: String, code: String) =
        client.post(BASE_URL + "user/registration") {
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

    suspend fun getProfile(userId: Long, token: String) = client.get(BASE_URL + "user/$userId") {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }

    suspend fun updateProfile(userId: Long, name: String, photo: Long?, file: Long?) =
        client.put(BASE_URL + "user/$userId") {
            contentType(ContentType.Application.Json)
            setBody(UpdateProfileRequestBody(name, photo, file))
        }

    suspend fun uploadFile(file: ByteArray, fileName: String, token: String) =
        client.post(BASE_URL + "file/upload") {
            headers {
                append(HttpHeaders.Authorization, token)
            }
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            "file",
                            file,
                            Headers.build {
                                append(HttpHeaders.ContentType, "*/*")
                                append(HttpHeaders.ContentDisposition, "filename=$fileName")
                            }
                        )
                    },
                    boundary = "WebAppBoundary"
                )
            )
        }

    suspend fun resetPassword(email: String, code: String, password: String) = client.post(BASE_URL + "user/reset") {
        url {
            parameters.append("email", email)
            parameters.append("token", code)
            parameters.append("password", password)
        }
    }
}