package com.example.profquest2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalProfQuest2Colors = staticCompositionLocalOf<ColorScheme> {
    error("Colors not provided")
}

val LocalProfQuest2Typography = staticCompositionLocalOf<Typography> {
    error("Typography not provided")
}

object ProfQuest2Theme {
    val colors: ColorScheme
        @Composable
        get() = LocalProfQuest2Colors.current

    val typography: Typography
        @Composable
        get() = LocalProfQuest2Typography.current
}

@Composable
fun ProfQuest2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = lightColorPalette
    val typography = typography

    CompositionLocalProvider(
        LocalProfQuest2Colors provides colors,
        LocalProfQuest2Typography provides typography,
        content = content
    )
}