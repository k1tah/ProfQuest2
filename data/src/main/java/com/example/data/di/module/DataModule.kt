package com.example.data.di.module

import android.content.Context
import android.util.Log
import com.example.data.api.ApiService
import com.example.data.api.company.CompanyService
import com.example.data.api.post.PostService
import com.example.data.api.vacancies.VacanciesService
import com.example.data.datasource.company.CompanyDataSource
import com.example.data.datasource.company.CompanyDataSourceImpl
import com.example.data.datasource.post.PostDataSource
import com.example.data.datasource.post.PostDataSourceImpl
import com.example.data.datasource.vacancies.VacanciesDataSource
import com.example.data.datasource.vacancies.VacanciesDataSourceImpl
import com.example.data.repository.AuthRepository
import com.example.data.repository.CompanyRepository
import com.example.data.repository.PostRepository
import com.example.data.repository.ProfileRepository
import com.example.data.repository.SettingsRepository
import com.example.data.repository.VacanciesRepository
import com.example.data.store.AuthStore
import com.example.data.store.SettingsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context) = SettingsRepository(
        SettingsStore(context)
    )

    @Provides
    @Singleton
    fun provideAuthRepository(@ApplicationContext context: Context) = AuthRepository(
        ApiService(provideHttpClient()),
        provideAuthStore(context)
    )

    @Provides
    @Singleton
    fun provideAuthStore(@ApplicationContext context: Context) = AuthStore(context)

    @Provides
    @Singleton
    fun provideProfileRepository() = ProfileRepository(ApiService(provideHttpClient()))

    @Provides
    @Singleton
    fun providePostRepository() = PostRepository(providePostDataSource())

    @Provides
    @Singleton
    fun provideCompanyRepository() = CompanyRepository(provideCompanyDataSource())

    @Provides
    @Singleton
    fun provideVacanciesRepository() = VacanciesRepository(provideVacanciesDataSource())

    @Provides
    @Singleton
    fun providePostDataSource(): PostDataSource = PostDataSourceImpl(providePostService())

    @Provides
    @Singleton
    fun provideCompanyDataSource(): CompanyDataSource = CompanyDataSourceImpl(provideCompanyService())

    @Provides
    @Singleton
    fun provideVacanciesDataSource(): VacanciesDataSource = VacanciesDataSourceImpl(provideVacanciesService())

    @Provides
    @Singleton
    fun providePostService() = PostService(provideHttpClient())

    @Provides
    @Singleton
    fun provideCompanyService() = CompanyService(provideHttpClient())

    @Provides
    @Singleton
    fun provideVacanciesService() = VacanciesService(provideHttpClient())

    @Provides
    @Singleton
    fun provideHttpClient() = HttpClient(OkHttp) {
        install(ContentNegotiation) { json() }
        install(HttpTimeout) {
            requestTimeoutMillis = 100000
            socketTimeoutMillis = 100000
            connectTimeoutMillis = 100000
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Ktor", "Ktor: $message \n ------------------------------ \n")
                }
            }
            level = LogLevel.BODY
        }
    }
}