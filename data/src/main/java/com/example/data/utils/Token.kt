package com.example.data.utils

import android.util.Base64
import java.io.UnsupportedEncodingException

fun getBasicToken(login: String?, password: String?): String {
    var data = ByteArray(0)
    try {
        data = ("${login}:${password}").toByteArray(charset("UTF-8"))
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
    }
    return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP)
}