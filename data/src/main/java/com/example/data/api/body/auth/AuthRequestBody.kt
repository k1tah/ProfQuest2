package com.example.data.api.body.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestBody(
    val login: String,
    val password: String
)
