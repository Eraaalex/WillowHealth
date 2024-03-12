package com.example.willowhealth.presentation.insights

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.willowhealth.model.HealthMetric
import com.example.willowhealth.presentation.main.MainViewModel
import com.example.willowhealth.presentation.ui.components.insights.BarChart
import com.example.willowhealth.presentation.ui.components.insights.SleepCard
import com.example.willowhealth.presentation.ui.components.insights.StepsCard
import org.koin.androidx.compose.koinViewModel


@Composable
fun InsightsScreen(viewModel: MainViewModel = koinViewModel()) {
    val lastSurvey by viewModel.lastSurvey
    val weekSleepData by viewModel.sleepDuration
    val steps by viewModel.steps
    LaunchedEffect(true) {
        viewModel.fetchDaySleepData()
        viewModel.fetchWeekSleepData()
        viewModel.fetchData(HealthMetric.STEPS)
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Log.d("MyApp", "sleepData in InsightsScreen: ${viewModel.lastSurvey.value}")
        StepsCard(value = steps)
        BasicSpacer()
        SleepCard(
            lastSurvey / 3600, lastSurvey % 3600 / 60
        )
        BasicSpacer()
        BarChart(values = weekSleepData)


    }

}

@Composable
fun InsightsScreenPreview() {
    InsightsScreen()
}

@Composable
fun BasicSpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}
