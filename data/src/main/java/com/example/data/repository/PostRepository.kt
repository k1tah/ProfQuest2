package com.example.data.repository

import com.example.data.datasource.PostDataSource
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
}