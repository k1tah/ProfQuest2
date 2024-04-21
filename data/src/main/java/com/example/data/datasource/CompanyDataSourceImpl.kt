package com.example.data.datasource

import com.example.data.api.company.CompanyService
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class CompanyDataSourceImpl @Inject constructor(private val service: CompanyService) : CompanyDataSource {
    override suspend fun getCompanies(token: String): HttpResponse = service.getCompaniesList(token)

    override suspend fun getCompany(token: String, id: Long): HttpResponse = service.getCompany(token, id)

    override suspend fun getCompanyPosts(
        token: String,
        id: Long,
        page: Int,
        size: Int
    ): HttpResponse {
        return service.getCompanyPosts(token, id, page, size)
    }
}