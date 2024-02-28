package com.example.data.di.module

import android.content.Context
import com.example.data.repository.SettingsRepository
import com.example.data.store.SettingsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSettingsStore(@ApplicationContext context: Context) = SettingsStore(context)

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context) = SettingsRepository(
        provideSettingsStore(context)
    )
}