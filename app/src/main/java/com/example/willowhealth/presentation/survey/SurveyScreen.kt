package com.example.willowhealth.presentation.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.willowhealth.R
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.presentation.ui.components.insights.MissionCard
import com.example.willowhealth.presentation.ui.components.survey.QuestionSleepCard
import com.example.willowhealth.utils.toLocalTime
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyScreen(viewModel: SurveyViewModel = koinViewModel()) {

    val uiState by viewModel.uiState
    val missions by viewModel.missions
    val scrollState = rememberScrollState()
    val timeStateForStart = rememberTimePickerState(22, 0, false)
    val timeStateForEnd = rememberTimePickerState(8, 0, false)
    val selectedDisturbances = remember { mutableStateOf(listOf<String>()) }
    val disturbances =
        listOf(
            stringResource(R.string.woke_up_frequently),
            stringResource(R.string.had_difficulty_falling_asleep),
            stringResource(R.string.experienced_nightmares)
        )

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuestionSleepCard(
            question = "Sleep time",
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

        if (missions.isNotEmpty()) {
            MissionCard(missions = missions, viewModel::updateMissionTemporaryCheckedState)
        }
        Button(
            onClick = {
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
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                question,
                fontSize = MaterialTheme.typography.h6.fontSize,
            )
            SleepQualityEstimationRow(state, onSleepQualitySelected = onSleepQualitySelected)
        }
    }

}

@Preview
@Composable
fun SleepQualityEstimationRowPreview() {
    SleepQualityEstimationRow(5, {})
}

@Composable
fun SleepQualityEstimationRow(
    initialQuality: Int,
    onSleepQualitySelected: (Int) -> Unit
) {
    val sleepQualityOptions = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val (selectedQuality, setSelectedQuality) = remember { mutableStateOf(initialQuality) } // Default value is 5


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {
            sleepQualityOptions.forEach { quality ->
                RadioButton(
                    selected = quality == selectedQuality,
                    onClick = {
                        onSleepQualitySelected(quality)
                        setSelectedQuality(quality)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.onSurface,
                        unselectedColor = MaterialTheme.colors.secondary,
                        disabledColor = MaterialTheme.colors.onSurface
                    ),
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .width(10.dp)
                )
            }
        }
        Row(modifier = Modifier.padding(start = 10.dp)) {
            sleepQualityOptions.forEach { quality ->
                Text(
                    text = quality.toString(),
                    modifier = Modifier
                        .width(34.dp)
                        .align(Alignment.CenterVertically),
                )
            }
        }
    }

}

@Composable
fun QuestionSleepDisturbances(
    disturbances: List<String>,
    onDisturbancesSelected: (List<String>) -> Unit,
    selectedDisturbances: MutableState<List<String>>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(5),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
//            Text(
//                text = ,
//                style = MaterialTheme.typography.h6,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
            Text(
                "Sleep Disturbances",
                fontSize = MaterialTheme.typography.h6.fontSize,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            disturbances.forEach { disturbance ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        val currentSelection = selectedDisturbances.value
                        if (currentSelection.contains(disturbance)) {
                            selectedDisturbances.value =
                                currentSelection.filter { it != disturbance }
                        } else {
                            selectedDisturbances.value = currentSelection + disturbance
                        }
                        onDisturbancesSelected(selectedDisturbances.value)
                    }
                ) {
                    Checkbox(
                        checked = selectedDisturbances.value.contains(disturbance),
                        onCheckedChange = { _ ->

                            val currentSelection = selectedDisturbances.value
                            if (currentSelection.contains(disturbance)) {
                                selectedDisturbances.value =
                                    currentSelection.filter { it != disturbance }
                            } else {
                                selectedDisturbances.value = currentSelection + disturbance
                            }
                            onDisturbancesSelected(selectedDisturbances.value)
                        }
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
}

