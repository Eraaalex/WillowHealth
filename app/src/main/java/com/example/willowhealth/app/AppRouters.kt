package com.example.willowhealth.app

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.willowhealth.presentation.ui.screens.LoginScreen
import com.example.willowhealth.presentation.ui.screens.MainScreen
import com.example.willowhealth.presentation.ui.screens.SurveyScreen
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

const val SPLASH_SCREEN = "SurveyScreen"
const val SETTINGS_SCREEN = "SettingsScreen"
const val LOGIN_SCREEN = "LoginScreen"
const val SIGN_UP_SCREEN = "SignUpScreen"
const val CHAT_SCREEN = "ChatScreen"
const val INSIGHTS_SCREEN = "InsightsScreen"

sealed class Screen {
    object SignUpScreen : Screen()
    object LoginScreen : Screen()
    object SurveyScreen : Screen()
    object MainScreen : Screen()

}


object AppRouter {

    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.MainScreen)

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }


}

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {

    KoinAndroidContext {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Crossfade(targetState = AppRouter.currentScreen) { currentState ->

                when (currentState.value) {
                    is Screen.LoginScreen -> {
                        LoginScreen()
                    }

                    is Screen.MainScreen -> {
                        Log.d("MyApp", "In navigate")
                        MainScreen()
                    }

                    is Screen.SurveyScreen -> {
                        SurveyScreen()
                    }

                    else -> {
                        LoginScreen()
                    }
                }
            }

        }
    }
}


