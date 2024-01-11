package com.example.willowhealth.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
//import androidx.compose.material3.MaterialTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
//    primary = Color.Black,
//    background = Color.Black,
//    secondary = Grey900, // ЦВЕТ КАРТОЧКИ
//    onBackground = Grey500,
//    onPrimary = Color.White,
//    onSecondary = Grey600
    primary = Color.Blue,
    background = Color.Cyan,
    secondary = Color.Yellow, // ЦВЕТ КАРТОЧКИ
    onBackground = Color.Red,
    onPrimary = Color.Magenta,
    onSecondary = Color.DarkGray

)

private val LightColorScheme = lightColorScheme(


)
private val DarkColorPalette = darkColors(
    primary = Color.White,
    onPrimary = Color.Black,
    background = Color.Black, // ЦВЕТ ФОНА
    surface = Grey900, //  ЦВЕТ КАРТОЧКИ
    secondary = Grey800,
    onBackground = Color.White,
    onSurface = Color.White, // ПЕРВОСТЕПЕННЫЙ ЦВЕТ КАРТЫ
    onSecondary = Grey700
)

private val LightColorPalette = lightColors(
    primary = Color.White,
    onPrimary = Color.Black,
    background = Grey300,
    secondary = Green500,
    surface = Color.White, // ЦВЕТ КАРТОЧКИ
    onBackground = Color.Black,
    onSurface = Color.Black, // ПЕВРОСТЕПЕННЫЙ ЦВЕТ КАРТЫ
    onSecondary = Grey700 // ВТОРОСТЕПЕННЫЙ
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