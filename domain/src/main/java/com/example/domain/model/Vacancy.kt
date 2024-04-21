package com.example.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Vacancy(
    val company: Long,
    val date: String,
    val description: String,
    val email: String,
    val id: Long,
    val isStroke: Boolean,
    @SerialName("number")
    val sort: String,
    @SerialName("vacancyName")
    val name: String
)