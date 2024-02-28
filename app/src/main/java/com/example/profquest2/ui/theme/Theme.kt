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

val LocalProfQuest2Image = staticCompositionLocalOf<Image> {
    error("Images not provided")
}

object ProfQuest2Theme {
    val colors: ColorScheme
        @Composable
        get() = LocalProfQuest2Colors.current

    val typography: Typography
        @Composable
        get() = LocalProfQuest2Typography.current

    val images: Image
        @Composable
        get() = LocalProfQuest2Image.current
}

@Composable
fun ProfQuest2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColorPalette else lightColorPalette
    val typography = typography
    val images = if (darkTheme) darkImages else lightImages

    CompositionLocalProvider(
        LocalProfQuest2Colors provides colors,
        LocalProfQuest2Typography provides typography,
        LocalProfQuest2Image provides images,
        content = content
    )
}