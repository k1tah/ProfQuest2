package com.example.profquest2.ui.theme

import androidx.annotation.DrawableRes
import com.example.profquest2.R

data class Image(
    @DrawableRes val background: Int,
    @DrawableRes val logo: Int
)

val lightImages = Image(background = R.drawable.bg, logo = R.drawable.ic_logo)

val darkImages = Image(background = R.drawable.bg_dark, logo = R.drawable.ic_logo_dark)