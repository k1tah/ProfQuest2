package com.example.data.datasource.vacancies

import io.ktor.client.statement.HttpResponse

interface VacanciesDataSource {
    suspend fun getVacancies(
        query: String,
        address: String,
        name: String,
        page: Int,
        size: Int,
        token: String
    ): HttpResponse

    suspend fun getFavouritesVacancies(token: String): HttpResponse

    suspend fun updateIsFavourite(id: Long, token: String): HttpResponse

    suspend fun sendResume(id: Long, token: String): HttpResponse
}