package com.example.data.repository

import com.example.data.api.ApiService
import com.example.domain.model.Profile
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getProfile(userId: Long) = apiService.getProfile(userId)

    suspend fun updateProfile(profile: Profile) = apiService.updateProfile(profile)

    suspend fun uploadFile(file: ByteArray, fileName: String, token: String) = apiService.uploadFile(file, fileName, token)
}