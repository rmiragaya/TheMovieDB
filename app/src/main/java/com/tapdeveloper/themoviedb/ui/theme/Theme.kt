package com.tapdeveloper.themoviedb.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = White,
    primaryVariant = Grey300,
    secondary = Purple500,
    surface = Black,
    onSurface = Grey500,
)

private val LightColorPalette = lightColors(
    primary = Black,
    primaryVariant = Grey300,
    secondary = Purple500,
    surface = White,
    onSurface = Grey500,
)

@Composable
fun TheMovieDBTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
