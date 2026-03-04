package com.cwmcarnyogalates.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = YogaPurple,
    onPrimary = YogaSurface,
    secondary = YogaGreen,
    onSecondary = YogaSurface,
    tertiary = YogaOrange,
    background = YogaBackground,
    surface = YogaSurface,
    onBackground = DarkPurple,
    onSurface = DarkPurple,
)

private val DarkColorScheme = darkColorScheme(
    primary = YogaPurpleLight,
    onPrimary = DarkBackground,
    secondary = YogaGreen,
    onSecondary = DarkBackground,
    tertiary = YogaOrange,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = YogaSurface,
    onSurface = YogaSurface,
)

@Composable
fun CWMYLTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content,
    )
}
