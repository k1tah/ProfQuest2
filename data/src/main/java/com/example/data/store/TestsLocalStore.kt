package com.example.data.store

import android.content.Context

class TestsLocalStore(context: Context) {
    private val sharedPref = context.getSharedPreferences(TESTS_PREFS, Context.MODE_PRIVATE)

    fun setGollandTestResult(result: String) = sharedPref.edit().putString(GOLLAND_TEST_RESULT_KEY, result).apply()

    fun getGollandTestResult() = sharedPref.getString(GOLLAND_TEST_RESULT_KEY, null)

    fun setYovayshiTestResult(result: String) = sharedPref.edit().putString(YOVAYSHI_TEST_RESULT_KEY, result).apply()

    fun getYovayshiTestResult() = sharedPref.getString(YOVAYSHI_TEST_RESULT_KEY, null)

    private companion object {
        const val TESTS_PREFS = "tests_local_store"
        const val GOLLAND_TEST_RESULT_KEY = "golland_test_result_key"
        const val YOVAYSHI_TEST_RESULT_KEY = "yovayshi_test_result_key"
    }
}