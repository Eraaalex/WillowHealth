package com.example.willowhealth.presentation.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import com.example.willowhealth.app.App
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.app.WillowHealth
import com.example.willowhealth.presentation.main.di.getMainModule
import com.example.willowhealth.presentation.settings.AppTheme
import com.example.willowhealth.presentation.settings.UserSettings
import com.example.willowhealth.presentation.ui.theme.WillowTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.get
import java.util.Calendar

const val TAG: String = "MyApp"

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "SurveyPrefs"
    lateinit var userSettings: UserSettings
    val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val module = getMainModule(this)
        (applicationContext as WillowHealth).koinApp.modules(module)

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        userSettings = get().get<UserSettings>()
        initialNavigation()
        setContent {
            Log.d(TAG, "setcontent")
            val theme = userSettings.themeStream.collectAsState()
            Log.d(TAG, "userSettings")
            val useDarkColors = when (theme.value) {
                AppTheme.MODE_DAY -> false
                AppTheme.MODE_NIGHT -> true
            }
            Log.d(TAG, "useDarkColors: $useDarkColors")
            WillowTheme(darkTheme = useDarkColors) {
                App(selectedTheme = theme.value,
                    onItemSelected = { theme -> userSettings.theme = theme })
            }
        }
    }

    private fun initialNavigation() {
        if (mainViewModel.getUser() != null) {
            val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            if ((hourOfDay in 5..12) && !SharedPreferencesManager.isSurveyCompleted()) {
                AppRouter.navigateTo(Screen.SurveyScreen)
            } else {
                AppRouter.navigateTo(Screen.SplashScreen)
            }

        } else {
            AppRouter.navigateTo(Screen.LoginScreen)
        }
    }


}


