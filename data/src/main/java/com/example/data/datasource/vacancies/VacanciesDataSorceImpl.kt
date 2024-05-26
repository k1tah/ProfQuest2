package com.example.data.datasource.vacancies

import com.example.data.api.vacancies.VacanciesService
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class VacanciesDataSourceImpl @Inject constructor(private val service: VacanciesService) : VacanciesDataSource {
    override suspend fun getVacancies(
        query: String,
        address: String,
        name: String,
        page: Int,
        size: Int,
        token: String
    ): HttpResponse = service.getVacancies(query, address, name, page, size, token)

    override suspend fun getFavouritesVacancies(token: String): HttpResponse = service.getFavouritesVacancies(token)

    override suspend fun updateIsFavourite(id: Long, token: String) = service.updateIsFavourite(id, token)

    override suspend fun sendResume(id: Long, token: String) = service.sendResume(id, token)

    override suspend fun getCompanyVacancies(id: Long, token: String): HttpResponse = service.getCompanyVacancies(id, token)
}