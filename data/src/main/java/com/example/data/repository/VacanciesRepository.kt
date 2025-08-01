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

    suspend fun getFavouritesVacancies(token: String) = dataSource.getFavouritesVacancies(token)

    suspend fun updateIsFavourite(id: Long, token: String) = dataSource.updateIsFavourite(id, token)

    suspend fun sendResume(id: Long, token: String) = dataSource.sendResume(id, token)

    suspend fun getCompanyVacancies(id: Long, token: String) = dataSource.getCompanyVacancies(id, token)
}