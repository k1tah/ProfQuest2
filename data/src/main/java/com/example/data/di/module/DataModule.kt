package com.example.data.di.module

import android.content.Context
import com.example.data.api.ApiService
import com.example.data.api.post.PostService
import com.example.data.datasource.PostDataSource
import com.example.data.datasource.PostDataSourceImpl
import com.example.data.repository.AuthRepository
import com.example.data.repository.PostRepository
import com.example.data.repository.ProfileRepository
import com.example.data.repository.SettingsRepository
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
        ApiService(),
        provideAuthStore(context)
    )

    @Provides
    @Singleton
    fun provideAuthStore(@ApplicationContext context: Context) = AuthStore(context)

    @Provides
    @Singleton
    fun provideProfileRepository() = ProfileRepository(ApiService())

    @Provides
    @Singleton
    fun providePostRepository() = PostRepository(providePostDataSource())

    @Provides
    @Singleton
    fun providePostDataSource(): PostDataSource = PostDataSourceImpl(providePostService())

    @Provides
    @Singleton
    fun providePostService() = PostService(provideHttpClient())

    @Provides
    @Singleton
    fun provideHttpClient() = HttpClient(OkHttp) {
        install(ContentNegotiation) { json() }
        install(HttpTimeout) {
            requestTimeoutMillis = 100000
            socketTimeoutMillis = 100000
            connectTimeoutMillis = 100000
        }
    }
}