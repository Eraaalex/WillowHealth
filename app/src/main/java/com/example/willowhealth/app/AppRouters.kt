package com.example.willowhealth.app

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.willowhealth.main.TAG
import com.example.willowhealth.presentation.MainScreen
import com.example.willowhealth.presentation.authentification.LoginScreen
import com.example.willowhealth.presentation.splash.SplashScreen
import com.example.willowhealth.presentation.survey.SurveyScreen
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
    object SplashScreen : Screen()

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
    val snackbarHostState = remember { SnackbarHostState() }
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "splashScreen") {
//        composable("splashScreen") { SplashScreen(navController) }
//        composable("loginScreen") { LoginScreen(navController) }
//        composable("mainScreen") { MainScreen(navController) }
//        composable("surveyScreen") { SurveyScreen(navController) }
//    }
    KoinAndroidContext {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Box {
                Crossfade(targetState = AppRouter.currentScreen, label = "") { currentState ->

                    when (currentState.value) {
                        is Screen.LoginScreen -> {
                            LoginScreen(snackbarHostState)
                        }

                        is Screen.MainScreen -> {
                            MainScreen()
                        }

                        is Screen.SurveyScreen -> {
                            SurveyScreen()
                        }
                        is Screen.SplashScreen -> {
                            Log.d(TAG, "AppRouter.navigateTo(Screen.SplashScreen)")
                            SplashScreen()
                        }

                        else -> {
                            LoginScreen(snackbarHostState)
                        }
                    }
                }
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 30.dp),
                    snackbar = { snackbarData ->
                        Snackbar(
                            snackbarData = snackbarData,
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.onPrimary,
                        )
                    })
            }
        }
    }
}


