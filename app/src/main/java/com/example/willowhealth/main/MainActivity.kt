package com.example.willowhealth.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.willowhealth.app.App
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.app.WillowHealth
import com.example.willowhealth.presentation.main.di.getMainModule
import com.example.willowhealth.presentation.ui.theme.WillowTheme
import java.util.Calendar
import org.koin.androidx.viewmodel.ext.android.viewModel

const val TAG: String = "MyApp"

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "SurveyPrefs"
    val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val module = getMainModule(this)
        (applicationContext as WillowHealth).koinApp.modules(module)

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        initialNavigation()
        setContent {
            WillowTheme {
                App()
            }
        }
    }

    private fun initialNavigation() {
        if (mainViewModel.getUser() != null) {
            val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            if ((hourOfDay > 20 || hourOfDay < 20) && !SharedPreferencesManager.isSurveyCompleted()) {
                AppRouter.navigateTo(Screen.SurveyScreen)
            } else {
                Log.d(TAG, "AppRouter.navigateTo(Screen.SplashScreen)")
                AppRouter.navigateTo(Screen.SplashScreen)
            }

        } else {
            AppRouter.navigateTo(Screen.LoginScreen)
        }
    }


}


