package com.example.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    @SerialName("user_id")
    val userId: Long?,
    val name: String,
    val voteName: String?,
    val nickname: String,
    val icon: File?,
    val text: String,
    val date: String,
    val files: List<File>?,
    val variants: List<String>?,
    val votes: List<Int>?,
    val likes: Int,
    val isLiked: Boolean,
    val isVoted: Boolean?,
    val vote: Int?,
    val expirationDate: String?
)