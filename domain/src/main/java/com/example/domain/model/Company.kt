package com.example.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val address: String?,
    @SerialName("companyId")
    val id: Long,
    @SerialName("companyName")
    val name: String,
    val date: String?,
    val description: String?,
    val email: String,
    val image: File?,
    val nickname: String,
    val phone: String?,
    @SerialName("short_desc")
    val shortDesc: String?,
    val socials: List<String>?,
    val worktime: String?
)