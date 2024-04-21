package com.example.data.datasource.company

import io.ktor.client.statement.HttpResponse

interface CompanyDataSource {
    suspend fun getCompanies(token: String): HttpResponse

    suspend fun getSchools(token: String): HttpResponse

    suspend fun getSchool(token: String, id: Long): HttpResponse

    suspend fun getCompany(token: String, id: Long): HttpResponse

    suspend fun getCompanyPosts(token: String, id: Long, page: Int, size: Int): HttpResponse
}