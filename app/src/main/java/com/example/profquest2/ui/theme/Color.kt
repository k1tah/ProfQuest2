package com.example.profquest2.ui.theme

import androidx.compose.ui.graphics.Color

data class ColorScheme(
    val colorPrimary: Color,
    val colorSecondary: Color,
    val colorSurface: Color,
    val colorTertiary: Color,
    val labelText: Color,
    val bodyText: Color
)


// Light colors
val red = Color(0xFFDC362E)
val gray = Color(0xFF959595)
val lightGray = Color(0xFFF2F2F2)
val grayMed = Color(0xFF9DB2CE)
val black = Color(0xFF000000)


val lightColorPalette = ColorScheme(red, gray, lightGray, grayMed, grayMed, black)
