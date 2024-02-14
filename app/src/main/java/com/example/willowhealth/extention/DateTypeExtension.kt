package com.example.willowhealth.extention

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Date
import java.util.Locale

fun Date.getFormattingTime(pattern: String = "yyyy-MM-dd"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}


@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.toLocalTime(): LocalTime {
    return LocalTime.of(this.hour, this.minute)
}