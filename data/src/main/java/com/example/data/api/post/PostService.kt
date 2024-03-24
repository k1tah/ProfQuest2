package com.example.data.api.post

import com.example.data.api.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.put
import io.ktor.http.HttpHeaders
import javax.inject.Inject

class PostService @Inject constructor(private val client: HttpClient) {
    suspend fun getPosts(
        search: String,
        company: String,
        address: String,
        page: Int,
        size: Int,
        token: String
    ) =
        client.get(BASE_URL + "post") {
            headers {
                append(HttpHeaders.Authorization, token)
            }
            url { parameters.append("search", search) }
            url { parameters.append("orgname", company) }
            url { parameters.append("address", address) }
            url { parameters.append("page", page.toString()) }
            url { parameters.append("size", size.toString()) }
        }

    suspend fun like(postId: Long, token: String) = client.put(BASE_URL + "post/$postId/like") {
        headers {
            append(HttpHeaders.Authorization, token)
        }
    }

    suspend fun vote(postId: Long, variant: Int, token: String) =
        client.put(BASE_URL + "post/$postId/vote") {
            headers {
                append(HttpHeaders.Authorization, token)
            }
            url { parameters.append("variant", variant.toString()) }
            url { parameters.append("id", postId.toString()) }
        }

    suspend fun undoVote(postId: Long, token: String) =
        client.delete(BASE_URL + "post/$postId/vote") {
            headers {
                append(HttpHeaders.Authorization, token)
            }
            url { parameters.append("id", postId.toString()) }
        }
}