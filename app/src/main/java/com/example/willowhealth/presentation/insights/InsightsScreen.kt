package com.example.willowhealth.presentation.insights

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
import com.example.willowhealth.presentation.ui.components.insights.BarChart
import com.example.willowhealth.presentation.ui.components.insights.MissionCard
import com.example.willowhealth.presentation.ui.components.insights.SleepCard
import com.example.willowhealth.presentation.ui.components.insights.StepsCard
import org.koin.androidx.compose.koinViewModel


@Composable
fun InsightsScreen(viewModel: InsightsViewModel = koinViewModel()) {
    val lastSurvey by viewModel.lastSurvey
    val weekSleepData by viewModel.sleepDuration
    val steps by viewModel.steps
    val missions by viewModel.missions
    LaunchedEffect(true) {
        viewModel.fetchDaySleepData()
        viewModel.fetchWeekSleepData()
        viewModel.fetchData(HealthMetric.STEPS)
        viewModel.fetchMissionData(1)
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        MissionCard(value = missions, viewModel::onMissionCheckedChange)
        BasicSpacer()
        StepsCard(value = steps)
        BasicSpacer()
        SleepCard(
            lastSurvey / 3600,
            lastSurvey % 3600 / 60
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
