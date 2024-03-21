package com.example.data.repository

import com.example.data.api.ApiService
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getProfile(userId: Long, token: String) = apiService.getProfile(userId, token)

    suspend fun updateProfile(userId: Long, name: String, photo: Long?, file: Long?) =
        apiService.updateProfile(userId, name, photo, file)

    suspend fun uploadFile(file: ByteArray, fileName: String = "file", token: String) =
        apiService.uploadFile(file, fileName, token)
}