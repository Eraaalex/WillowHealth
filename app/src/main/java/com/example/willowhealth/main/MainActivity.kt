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
import com.example.willowhealth.data.datasource.FirebaseAuthDataSource
import com.example.willowhealth.presentation.main.di.getMainModule
import com.example.willowhealth.presentation.ui.theme.WillowTheme
import java.util.Calendar

const val TAG: String = "MyApp"

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "SurveyPrefs"
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
        if (FirebaseAuthDataSource.getCurrentUser() != null) {
            val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            if ((hourOfDay > 22 || hourOfDay < 21) && !SharedPreferencesManager.isSurveyCompleted()) {
                AppRouter.navigateTo(Screen.SurveyScreen)
            } else {
                Log.d(TAG, "AppRouter.navigateTo(Screen.MainScreen)")
                AppRouter.navigateTo(Screen.MainScreen)
            }

        } else {
            AppRouter.navigateTo(Screen.LoginScreen)
        }
    }


}


