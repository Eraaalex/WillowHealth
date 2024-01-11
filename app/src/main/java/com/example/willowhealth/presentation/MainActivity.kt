package com.example.willowhealth.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.willowhealth.HealthMetric
import com.example.willowhealth.presentation.ui.theme.WillowTheme
import com.example.willowhealth.app.WillowHealth
import com.example.willowhealth.presentation.SurveyViewModel
import com.example.willowhealth.presentation.main.di.getMainModule
import com.example.willowhealth.presentation.ui.screens.SurveyScreen
//import com.example.willowhealth.ui.theme.WillowHealthTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()
//    private val surveyViewModel by viewModel<SurveyViewModel>()
private lateinit var surveyViewModel: SurveyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val module = getMainModule(this)
        (applicationContext as WillowHealth).koinApp.modules(module) // integration
        val surveyViewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)

        setContent {
            WillowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Navigation(viewModel = viewModel)
                    SurveyScreen(surveyViewModel)

                }
            }
        }
        viewModel.fetchData(HealthMetric.STEPS)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun StepsScreen(viewModel: MainViewModel) {
    val stepsData = viewModel.steps.observeAsState()
    stepsData.value?.let {
        Text(text = it.toString())
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WillowTheme {
        Greeting("Android")
    }
}