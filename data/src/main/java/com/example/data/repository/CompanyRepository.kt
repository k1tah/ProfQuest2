package com.example.data.repository

import com.example.data.datasource.company.CompanyDataSource
import javax.inject.Inject

class CompanyRepository @Inject constructor(private val companyDataSource: CompanyDataSource) {
    suspend fun getSchools(token: String) = companyDataSource.getSchools(token)

    suspend fun getSchool(token: String, id: Long) = companyDataSource.getSchool(token, id)

    suspend fun getCompanies(token: String) = companyDataSource.getCompanies(token)

    suspend fun getCompany(token: String, id: Long) = companyDataSource.getCompany(token, id)

    suspend fun getCompanyPosts(token: String, id: Long, page: Int, size: Int) =
        companyDataSource.getCompanyPosts(token, id, page, size)
}