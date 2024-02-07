package com.example.willowhealth.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.willowhealth.app.App
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.app.WillowHealth
import com.example.willowhealth.presentation.main.di.getMainModule
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

//    private val viewModel by viewModel<MainViewModel>()
//    private val surveyViewModel by viewModel<SurveyViewModel>()
//    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val module = getMainModule(this)
        (applicationContext as WillowHealth).koinApp.modules(module) // integration

//        if (FirebaseAuth.getInstance().currentUser != null) {
//            AppRouter.navigateTo(Screen.MainScreen)
//        }
        setContent {
            App()
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