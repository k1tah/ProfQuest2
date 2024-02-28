package com.example.data.store

import android.content.Context

class SettingsStore(context: Context) {
    private val sharedPref = context.getSharedPreferences(THEME_PREFS, Context.MODE_PRIVATE)

    fun setDarkTheme(isDarkTheme: Boolean) = sharedPref.edit().putBoolean(DARK_THEME_KEY, isDarkTheme).apply()

    fun isDarkTheme() = sharedPref.getBoolean(DARK_THEME_KEY, false)

    private companion object {
        const val THEME_PREFS = "theme_prefs"
        const val DARK_THEME_KEY = "dark_theme"
    }
}