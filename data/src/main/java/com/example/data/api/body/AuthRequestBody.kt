package com.example.data.api.body

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestBody(
    val login: String,
    val password: String
)
