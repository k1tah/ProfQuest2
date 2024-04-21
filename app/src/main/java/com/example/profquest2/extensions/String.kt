package com.example.profquest2.extensions

import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(): String? {
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale("ru")).parse(this)
    return date?.let { SimpleDateFormat("yyyy.MM.dd HH:mm", Locale("ru")).format(it) }
}