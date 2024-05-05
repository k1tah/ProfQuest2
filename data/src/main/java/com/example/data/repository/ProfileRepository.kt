package com.example.data.repository

import com.example.data.api.ApiService
import com.example.data.store.TestsLocalStore
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val apiService: ApiService,
    private val testsStore: TestsLocalStore
) {
    suspend fun getProfile(token: String) = apiService.getProfile(token)

    suspend fun updateProfile(name: String, photo: Long?, file: Long?, token: String) =
        apiService.updateProfile(name, photo, file, token)

    suspend fun uploadFile(file: ByteArray, fileName: String = "file", token: String) =
        apiService.uploadFile(file, fileName, token)

    fun getTestResults(): Pair<String?, String?> = Pair(
        testsStore.getGollandTestResult(),
        testsStore.getYovayshiTestResult()
    )

    fun setYovayshiTestResult(result: String) = testsStore.setYovayshiTestResult(result)

    fun setGollandTestResult(result: String) = testsStore.setGollandTestResult(result)
}