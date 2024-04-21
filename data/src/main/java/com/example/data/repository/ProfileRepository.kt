package com.example.data.repository

import com.example.data.api.ApiService
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getProfile(token: String) = apiService.getProfile(token)

    suspend fun updateProfile(name: String, photo: Long?, file: Long?, token: String) =
        apiService.updateProfile(name, photo, file, token)

    suspend fun uploadFile(file: ByteArray, fileName: String = "file", token: String) =
        apiService.uploadFile(file, fileName, token)
}