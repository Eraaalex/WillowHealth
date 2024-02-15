package com.example.willowhealth.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
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
import java.util.Calendar

const val TAG: String = "MyApp"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val module = getMainModule(this)
        (applicationContext as WillowHealth).koinApp.modules(module) // integration

        initialNavigation()
        setContent {
            WillowTheme {
                App()
            }

        }
    }

    fun initialNavigation() {
        if (FirebaseAuthDataSource.getCurrentUser() != null) {
            val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            if (hourOfDay > 22 || hourOfDay < 8) {
                AppRouter.navigateTo(Screen.SurveyScreen)
            } else {
                AppRouter.navigateTo(Screen.MainScreen)
            }

        } else {
            AppRouter.navigateTo(Screen.LoginScreen)
        }
    }
}


@Composable
fun StepsScreen(viewModel: MainViewModel) {
    val stepsData = viewModel.steps.observeAsState()
    stepsData.value?.let {
        Text(text = it.toString())
    }
}