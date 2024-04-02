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
    background = Color.Black,
    surface = Grey1000,
    secondary = Grey800,
    onBackground = Color.White,
    onSurface = Color.White,
    onSecondary = Grey700,
    primaryVariant = Grey600,
    error = Color.Red,
    secondaryVariant = Grey1100
)

private val LightColorPalette = lightColors(
    primary = Color.White,
    onPrimary = Color.Black,
    background = Grey300,
    secondary = Grey700,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onSecondary = Grey800,
    primaryVariant = Grey600,
    secondaryVariant = Color.White
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