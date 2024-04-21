package com.example.data.api.vacancies

import com.example.data.api.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import javax.inject.Inject

class VacanciesService @Inject constructor(private val client: HttpClient) {
    suspend fun getVacancies(
        query: String,
        address: String,
        name: String,
        page: Int,
        size: Int,
        token: String
    ) = client.get(BASE_URL + "vacancy") {
        parameter("query", query)
        parameter("address", address)
        parameter("name", name)
        parameter("page", page)
        parameter("size", size)
        header(HttpHeaders.Authorization, token)
    }
}