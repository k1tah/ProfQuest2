package com.example.data.api.company

import com.example.data.api.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import javax.inject.Inject

class CompanyService @Inject constructor(private val client: HttpClient) {
    suspend fun getCompaniesList(token: String) = client.get(BASE_URL + "company") {
        header(HttpHeaders.Authorization, token)
    }

    suspend fun getCompany(token: String, id: Long) = client.get(BASE_URL + "company/$id") {
        header(HttpHeaders.Authorization, token)
    }

    suspend fun getCompanyPosts(token: String, id: Long, page: Int, size: Int) =
        client.get(BASE_URL + "company/$id/posts") {
            header(HttpHeaders.Authorization, token)
            url.parameters.apply {
                append("page", page.toString())
                append("size", size.toString())
            }
        }
}