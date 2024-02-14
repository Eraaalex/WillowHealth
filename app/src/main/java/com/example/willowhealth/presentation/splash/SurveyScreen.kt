package com.example.willowhealth.presentation.splash

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.extention.toLocalTime
import com.example.willowhealth.presentation.main.TAG
import com.example.willowhealth.presentation.ui.components.survey.QuestionSleepCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyScreen(viewModel: SurveyViewModel = viewModel()) {

    val uiState by viewModel.uiState

    val timeStateForStart = rememberTimePickerState(22, 0, false)
    val timeStateForEnd = rememberTimePickerState(8, 0, false)
    var selectedDisturbances = remember { mutableStateOf(listOf<String>()) }
    val disturbances =
        listOf("Woke up frequently", "Had difficulty falling asleep", "Experienced nightmares")

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuestionSleepCard(
            question = "@string/question_sleep_time",
            timeStateForStart = timeStateForStart,
            timeStateForEnd = timeStateForEnd,
        )
        QuestionEstimationSleep(
            "Sleep Quality",
            uiState.estimationSleep,
            viewModel::onRadioButtonClick
        )
        QuestionSleepDisturbances(
            disturbances = disturbances,
            onDisturbancesSelected = {},
            selectedDisturbances = selectedDisturbances
        )

        Button(
            onClick = {
                Log.d(TAG, "onSaveClick: in Button")
                viewModel.onSaveClick(
                    timeStateForStart.toLocalTime(),
                    timeStateForEnd.toLocalTime()
                )
                AppRouter.navigateTo(Screen.MainScreen)
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


@Composable
fun QuestionEstimationSleep(question: String, state: Int, onSleepQualitySelected: (Int) -> Unit) {
    var sleepQuality: Int = 0
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(5),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(14.dp, 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                question,
                fontSize = MaterialTheme.typography.h6.fontSize,
            )
            SleepQualityEstimationRow(state, onSleepQualitySelected = { quality ->
                sleepQuality = quality
            })
        }
    }

}

@Composable
fun SleepQualityEstimationRow(
    initialQuality: Int,
    onSleepQualitySelected: (Int) -> Unit
) {
    val sleepQualityOptions = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val (selectedQuality, setSelectedQuality) = remember { mutableStateOf(initialQuality) } // Default value is 5

    Row {
        sleepQualityOptions.forEach { quality ->
            RadioButton(
                selected = quality == selectedQuality,
                onClick = {
                    onSleepQualitySelected(quality)
                    setSelectedQuality(quality)
                },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Blue),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun QuestionSleepDisturbances(
    disturbances: List<String>,
    onDisturbancesSelected: (List<String>) -> Unit,
    selectedDisturbances: MutableState<List<String>>
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Sleep Disturbances",
            style = MaterialTheme.typography.h6,
            color = Color.Black
        )
        disturbances.forEach { disturbance ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    if (selectedDisturbances.value.contains(disturbance)) {
                        selectedDisturbances.value =
                            selectedDisturbances.value.filter { it != disturbance }
                    } else {
                        selectedDisturbances.value = selectedDisturbances.value + disturbance
                    }
                    onDisturbancesSelected(selectedDisturbances.value)
                }
            ) {
                Checkbox(
                    checked = selectedDisturbances.value.contains(disturbance),
                    onCheckedChange = null
                )
                Text(
                    text = disturbance,
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}