package com.example.profquest2.utils

import android.content.Context
import android.widget.Toast

fun Context.showShortToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()