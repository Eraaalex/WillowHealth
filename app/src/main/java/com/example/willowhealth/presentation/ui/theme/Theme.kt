package com.example.willowhealth.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.Black,
    onPrimary = Color.White,
    background = Color.Black, // ЦВЕТ ФОНА
    surface = Grey1000, //  ЦВЕТ КАРТОЧКИ
    secondary = Grey800, // цвет блокнутой кнопки
    onBackground = Color.White,
    onSurface = Color.White,
    onSecondary = Grey700,
    primaryVariant = Grey600,
)

private val LightColorPalette = lightColors(
    primary = Color.White,
    onPrimary = Color.Black,
    background = Grey300,
    secondary = Grey700,
    surface = Color.White, // ЦВЕТ КАРТОЧКИ
    onBackground = Color.Black,
    onSurface = Color.Black, // ПЕВРОСТЕПЕННЫЙ ЦВЕТ КАРТЫ
    onSecondary = Grey800, // ВТОРОСТЕПЕННЫЙ
    primaryVariant = Grey600
)

@Composable
fun WillowTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}