package com.example.data.api.body

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequestBody(val name: String?, val photo: String?, val file: Long?)
