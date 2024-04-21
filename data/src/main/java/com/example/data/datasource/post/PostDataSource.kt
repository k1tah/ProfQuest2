package com.example.data.datasource.post

import io.ktor.client.statement.HttpResponse

interface PostDataSource {
    suspend fun getPosts(
        search: String,
        company: String,
        address: String,
        page: Int,
        size: Int,
        token: String
    ): HttpResponse

    suspend fun like(postId: Long, token: String): HttpResponse

    suspend fun vote(postId: Long, variant: Int, token: String): HttpResponse

    suspend fun undoVote(postId: Long, token: String): HttpResponse
}