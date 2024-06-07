package com.example.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class School(
    val date: String,
    val description: String,
    val email: String,
    val userID: Long,
    val institutionID: Long,
    val image: File?,
    val name: String,
    val nickname: String,
    val phone: String,
    @SerialName("short_desc")
    val shortDesc: String,
    val socials: List<String>,
    val worktime: String
)