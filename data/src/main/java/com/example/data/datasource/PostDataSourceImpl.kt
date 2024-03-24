package com.example.data.datasource

import com.example.data.api.post.PostService
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(private val service: PostService) : PostDataSource {
    override suspend fun getPosts(
        search: String,
        company: String,
        address: String,
        page: Int,
        size: Int,
        token: String
    ): HttpResponse = service.getPosts(search, company, address, page, size, token)

    override suspend fun like(postId: Long, token: String): HttpResponse =
        service.like(postId, token)

    override suspend fun vote(postId: Long, variant: Int, token: String): HttpResponse =
        service.vote(postId, variant, token)

    override suspend fun undoVote(postId: Long, token: String): HttpResponse =
        service.undoVote(postId, token)
}