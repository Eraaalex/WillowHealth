package com.example.willowhealth.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.example.willowhealth.ui.theme.Green900
//import com.example.willowhealth.ui.theme.WillowHealthTheme
import com.example.willowhealth.ui.theme.WillowTheme

@Composable
fun SurveyScreen() {


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionCard() {
    val timeState = rememberTimePickerState(8, 0, true)
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(0.dp, Color.Black),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "How many hours did you sleep?",
                fontSize = 25.sp,
                modifier = Modifier.fillMaxWidth()
            )

            TimeInput(state = timeState)
        }
    }
}


@Composable
@Preview
fun QuestionCardPreview(){
    WillowTheme {
        QuestionCard()
    }
}