package com.example.data.repository

import com.example.data.store.SettingsStore
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val settingsStore: SettingsStore) {
    fun setDarkTheme(isDarkTheme: Boolean) = settingsStore.setDarkTheme(isDarkTheme)

    fun getDarkTheme() = settingsStore.isDarkTheme()
}