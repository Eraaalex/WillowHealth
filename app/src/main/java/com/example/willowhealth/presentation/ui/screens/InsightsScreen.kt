package com.example.willowhealth.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.willowhealth.presentation.ui.components.insights.StepsCard
import com.example.willowhealth.service.DateTimeWithMetric

@Composable
fun InsightsScreen(stepsData: DateTimeWithMetric?) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Log.d("MyApp", "stepsData in InsightsScreen: $stepsData")
        StepsCard(
            stepsData?.get("2024-01-10")?.get("23:40")?.get("steps") ?: 0
        )
    }

}