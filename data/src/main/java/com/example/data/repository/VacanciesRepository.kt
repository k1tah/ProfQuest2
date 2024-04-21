package com.example.data.repository

import com.example.data.datasource.vacancies.VacanciesDataSource

class VacanciesRepository(private val dataSource: VacanciesDataSource) {
    suspend fun getVacancies(
        query: String,
        address: String,
        name: String,
        page: Int,
        size: Int,
        token: String
    ) = dataSource.getVacancies(query, address, name, page, size, token)
}