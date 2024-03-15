package com.example.data.store

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class AuthStore (context: Context) {
    private val masterKey = MasterKey.Builder(context).apply {
        setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    }.build()
    private val prefs = EncryptedSharedPreferences.create(
        context, PREFS_FILENAME, masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    private val prefsEditor = prefs.edit()

    fun setUserId(id: Long) {
        prefsEditor.putLong(USER_ID_KEY, id).apply()
    }

    fun getUserId() = prefs.getLong(USER_ID_KEY, -1L)


    fun setLogin(login: String) {
        prefsEditor.putString(LOGIN_KEY, login).apply()
    }

    fun getLogin() = prefs.getString(LOGIN_KEY, null)


    fun setPassword(password: String) {
        prefsEditor.putString(PASSWORD_KEY, password).apply()
    }

    fun getPassword() = prefs.getString(PASSWORD_KEY, null)


    private companion object {
        const val USER_ID_KEY = "userId"
        const val LOGIN_KEY = "login"
        const val PASSWORD_KEY = "password"
        const val PREFS_FILENAME = "prefs"
    }
}