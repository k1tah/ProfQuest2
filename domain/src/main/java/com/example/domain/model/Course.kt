package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val address: String,
    val cost: String,
    val date: String,
    val days: String,
    val description: String,
    val email: String,
    val icon: File?,
    val id: Long,
    val length: String,
    val name: String,
    val nickname: String,
    val phone: String,
    val uname: String
)