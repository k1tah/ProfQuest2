package com.example.data.api

import com.example.data.api.body.AuthRequestBody
import com.example.data.api.body.FileRequestBody
import com.example.data.api.body.UpdateProfileRequestBody
import com.example.domain.model.Profile
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.headers
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

    suspend fun getProfile(userId: Long) = client.get(BASE_URL + "user/$userId")

    suspend fun updateProfile(profile: Profile) = client.put(BASE_URL + "user/${profile.id}") {
        contentType(ContentType.Application.Json)
        setBody(profile.toUpdateRequestBody())
    }

    suspend fun uploadFile(file: ByteArray, fileName: String, token: String) =
        client.post(BASE_URL + "file/upload") {
            headers {
                append(HttpHeaders.Authorization, token)
            }
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("file", file, Headers.build {
                            append(HttpHeaders.ContentType, "*/*")
                            append(HttpHeaders.ContentDisposition, "filename=$fileName")
                        })
                    },
                    boundary = "WebAppBoundary"
                )
            )
        }

    private fun Profile.toUpdateRequestBody() = UpdateProfileRequestBody(name, photo, file?.id)

    companion object {
        const val BASE_URL = "http://192.168.199.131:8080/"
    }
}