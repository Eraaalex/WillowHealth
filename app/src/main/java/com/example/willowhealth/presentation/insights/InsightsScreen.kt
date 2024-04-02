package com.example.willowhealth.presentation.insights

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.willowhealth.R
import com.example.willowhealth.main.TAG
import com.example.willowhealth.presentation.ui.components.insights.BarChart
import com.example.willowhealth.presentation.ui.components.insights.CaloriesCard
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
    val calories by viewModel.calories
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MissionCard(missions = missions, viewModel::onMissionCheckedChange)
        BasicSpacer()
        CaloriesCard(value = calories)
        BasicSpacer()
        StepsCard(value = steps)
        BasicSpacer()
        SleepCard(
            stringResource(R.string.sleep_duration),
            lastSurvey,
            lastSurvey
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
