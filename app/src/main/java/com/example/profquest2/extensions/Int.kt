package com.example.profquest2.extensions

import android.content.res.Resources

fun Int.toPx() = this * Resources.getSystem().displayMetrics.density.toInt()