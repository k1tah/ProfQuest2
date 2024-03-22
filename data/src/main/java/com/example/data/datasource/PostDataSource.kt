package com.example.data.datasource

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
}