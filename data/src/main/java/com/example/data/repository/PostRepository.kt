package com.example.data.repository

import com.example.data.datasource.post.PostDataSource
import javax.inject.Inject

class PostRepository @Inject constructor(private val postDataSource: PostDataSource) {
    suspend fun getPosts(
        search: String,
        company: String,
        address: String,
        page: Int,
        size: Int,
        token: String
    ) = postDataSource.getPosts(search, company, address, page, size, token)

    suspend fun like(postId: Long, token: String) = postDataSource.like(postId, token)

    suspend fun vote(postId: Long, variant: Int, token: String) = postDataSource.vote(postId, variant, token)

    suspend fun undoVote(postId: Long, token: String) = postDataSource.undoVote(postId, token)
}