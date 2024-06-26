package com.example.data.api.body.resetPassword

import kotlinx.serialization.Serializable

@Serializable
data class ResetRequestBody(
    val email: String,
    val token: String,
    val password: String)
