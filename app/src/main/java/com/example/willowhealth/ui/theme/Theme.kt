package com.example.willowhealth.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
//import androidx.compose.material3.MaterialTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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
    primary = Color.White,
    onPrimary = Color.Black,
    background = Grey300,
    surface = Color.White, // ЦВЕТ КАРТОЧКИ
    onBackground = Color.Black,
    onSurface = Color.Black, // ПЕВРОСТЕПЕННЫЙ ЦВЕТ КАРТЫ
    onSecondary = Grey800 // ВТОРОСТЕПЕННЫЙ

)
private val DarkColorPalette = darkColors(
    primary = Color.White,
    onPrimary = Color.Black,
    background = Color.Black, // ЦВЕТ ФОНА
    surface = Grey900, //  ЦВЕТ КАРТОЧКИ
    onBackground = Color.White,
    onSurface = Color.White, // ПЕРВОСТЕПЕННЫЙ ЦВЕТ КАРТЫ
    onSecondary = Grey800
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = Color.White,
    background = Grey300,
    secondary = Color.White, // ЦВЕТ КАРТОЧКИ
    onBackground = Grey500,
    onPrimary = Color.Black,
    onSecondary = Grey600
)

//@Composable
//fun WillowHealthTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
//}

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