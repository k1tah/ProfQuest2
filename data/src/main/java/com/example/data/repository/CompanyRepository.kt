package com.example.data.repository

import com.example.data.datasource.CompanyDataSource
import javax.inject.Inject

class CompanyRepository @Inject constructor(private val companyDataSource: CompanyDataSource) {
    suspend fun getCompanies(token: String) = companyDataSource.getCompanies(token)

    suspend fun getCompany(token: String, id: Long) = companyDataSource.getCompany(token, id)

    suspend fun getCompanyPosts(token: String, id: Long, page: Int, size: Int) =
        companyDataSource.getCompanyPosts(token, id, page, size)
}