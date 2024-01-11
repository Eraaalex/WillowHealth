package com.example.willowhealth.presentation.ui.components.survey

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.willowhealth.presentation.ui.theme.Grey300
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionCard(
    question: String,
    timeStateForStart: TimePickerState,
    timeStateForEnd: TimePickerState
) {
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
                fontSize = MaterialTheme.typography.h6.fontSize, // header size
            )
            Text(
                "From",
                fontSize = MaterialTheme.typography.body1.fontSize, // subheader size
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onSecondary
            )
            TimeInputField(timeStateForStart)
            Text(
                "To",
                fontSize = MaterialTheme.typography.body1.fontSize,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onSecondary
            )
            TimeInputField(timeStateForEnd)
            Text(
                text = "Duration is ${
                    timeStateForEnd.hour - timeStateForStart.hour +
                            if (timeStateForEnd.hour < timeStateForStart.hour)
                                24
                            else
                                0
                } : " +
                        "${timeStateForEnd.minute - timeStateForStart.minute}"
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputField(timeState: TimePickerState) {
    TimeInput(
        state = timeState,
        colors = TimePickerDefaults.colors(
            selectorColor = MaterialTheme.colors.secondary,
            containerColor = MaterialTheme.colors.secondary,
            timeSelectorSelectedContainerColor = MaterialTheme.colors.background,
            timeSelectorSelectedContentColor = MaterialTheme.colors.onSurface,
            timeSelectorUnselectedContainerColor = MaterialTheme.colors.background,
            timeSelectorUnselectedContentColor = MaterialTheme.colors.onSecondary,
            periodSelectorBorderColor = MaterialTheme.colors.secondary,
            periodSelectorSelectedContainerColor = MaterialTheme.colors.background,
            periodSelectorSelectedContentColor = MaterialTheme.colors.onSurface,
            periodSelectorUnselectedContainerColor = MaterialTheme.colors.background,
            periodSelectorUnselectedContentColor = MaterialTheme.colors.onSecondary,
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
fun timePickerStateToLocalDateTime(timePickerState: TimePickerState): LocalDateTime {
    val currentDate = LocalDate.now()
    val time = LocalTime.of(timePickerState.hour, timePickerState.minute)
    return if (timePickerState.hour > 12) {
        LocalDateTime.of(currentDate.minusDays(1), time)
    } else {
        LocalDateTime.of(currentDate, time)
    }
}
