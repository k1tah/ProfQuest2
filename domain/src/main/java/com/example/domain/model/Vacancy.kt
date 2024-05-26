package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Vacancy(
    val about: String,
    val address: String,
    val company: Long,
    val companyName: String,
    val date: String,
    val description: String,
    val email: String,
    val icon: File?,
    val id: Long,
    val isFavourite: Boolean,
    val isStroke: Boolean,
    val nickname: String,
    val number: String,
    val requirements: String,
    val salary: Int,
    val vacancyName: String,
    val worktime: String
)