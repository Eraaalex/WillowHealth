package com.example.willowhealth.presentation.insights

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.willowhealth.model.HealthMetric
import com.example.willowhealth.presentation.main.MainViewModel
import com.example.willowhealth.presentation.ui.components.insights.StepsCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun InsightsScreen(viewModel: MainViewModel = koinViewModel()) {
    val stepsData = viewModel.steps.value
    viewModel.fetchData(HealthMetric.STEPS)
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Log.d("MyApp", "stepsData in InsightsScreen: $stepsData")
        StepsCard(
            stepsData?.get("2024-01-10")?.get("23:40")?.get("steps") ?: 0
        )
    }

}