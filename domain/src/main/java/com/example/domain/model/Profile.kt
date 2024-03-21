package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: Long,
    val name: String?,
    val photo: File?,
    val file: File?
)

@Serializable
data class File(val id: Long, val name: String, val type: String)