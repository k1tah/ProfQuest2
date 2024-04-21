package com.example.data.api

import com.example.data.api.body.UpdateProfileRequestBody
import com.example.data.api.body.auth.AuthRequestBody
import com.example.data.utils.getBasicToken
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import javax.inject.Inject

class ApiService @Inject constructor(private val client: HttpClient) {

    suspend fun signIn(login: String, password: String) = client.post(BASE_URL + "user/signin") {
        contentType(ContentType.Application.Json)
        header(HttpHeaders.Authorization, getBasicToken(login, password))
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

    suspend fun getProfile(token: String) = client.get(BASE_URL + "user") {
        header(HttpHeaders.Authorization, token)
    }

    suspend fun updateProfile(name: String, photo: Long?, file: Long?, token: String) =
        client.put(BASE_URL + "user") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, token)
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

    suspend fun resetPassword(email: String, code: String, password: String) =
        client.put(BASE_URL + "user/reset") {
            url {
                parameters.append("email", email)
                parameters.append("token", code)
                parameters.append("password", password)
            }
        }
}