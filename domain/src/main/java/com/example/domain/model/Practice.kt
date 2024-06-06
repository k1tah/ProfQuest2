package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Practice(
    val address: String,
    val company: Long,
    val companyName: String,
    val date: String,
    val documents: String,
    val email: String,
    val icon: File?,
    val id: Long,
    val name: String,
    val nickname: String,
    val phone: String,
    val specialities: String
)