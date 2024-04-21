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
}