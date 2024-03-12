package com.example.willowhealth.presentation.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.willowhealth.app.App
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.app.WillowHealth
import com.example.willowhealth.data.datasource.FirebaseAuthDataSource
import com.example.willowhealth.presentation.main.di.getMainModule
import com.example.willowhealth.presentation.ui.theme.WillowTheme
import java.time.DayOfWeek
import java.util.Calendar

const val TAG: String = "MyApp"

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val module = getMainModule(this)
        (applicationContext as WillowHealth).koinApp.modules(module) // integration

        sharedPreferences = getSharedPreferences("SurveyPrefs", Context.MODE_PRIVATE)


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
            if ((hourOfDay > 22 || hourOfDay < 21) ) { // TIME TO SURVEY &&!SharedPreferencesManager.isSurveyCompleted()
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


