package com.example.profquest2.ui.theme

import androidx.compose.ui.graphics.Color

data class ColorScheme(
    val primary: Color,
    val secondary: Color,
    val surface: Color,
    val tertiary: Color,
    val onSurface: Color
)


// Light colors
val red = Color(0xFFDC362E)
val gray = Color(0xFF959595)
val lightGray = Color(0xFFF2F2F2)
val grayMed = Color(0xFF9DB2CE)
val black = Color(0xFF000000)

val darkBlue = Color(0xFF30384F)
val white = Color(0xFFFFFFFF)


val lightColorPalette = ColorScheme(red, gray, lightGray, grayMed, black)

val darkColorPalette = ColorScheme(red, gray, darkBlue, grayMed, white)
