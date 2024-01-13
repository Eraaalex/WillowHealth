package com.example.willowhealth.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.willowhealth.presentation.SurveyViewModel
import com.example.willowhealth.presentation.ui.components.survey.QuestionCard
import java.time.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyScreen(viewModel: SurveyViewModel) {
    // State variables for start and end time
    val timeStateForStart = rememberTimePickerState(22, 0, false)
    val timeStateForEnd = rememberTimePickerState(8, 0, false)

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuestionCard(
            question = "@string/question_sleep_time",
            timeStateForStart = timeStateForStart,
            timeStateForEnd = timeStateForEnd,
        )
        Button(
            onClick = {
                viewModel.updateTimeData(
                    startTime = LocalTime.of(timeStateForStart.hour, timeStateForStart.minute),
                    endTime = LocalTime.of(timeStateForEnd.hour, timeStateForEnd.minute)
                )
            },
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .defaultMinSize(90.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
            shape = RoundedCornerShape(15)
            ) {
            Text("Send!", fontSize = MaterialTheme.typography.h6.fontSize)
        }
    }
}