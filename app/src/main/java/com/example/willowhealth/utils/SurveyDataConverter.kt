package com.example.willowhealth.utils

import android.util.Log
import com.example.willowhealth.model.SurveyData
import com.example.willowhealth.presentation.main.TAG
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

public fun SurveyData.toGPTRequestForm(): String {
    if (this.startSleepTime == 0 || this.endSleepTime == 0) {
        return "I don't have enough data to generate a report. Please fill out the survey."
    }

    val text: String = "Date: " + this.timestamp.toLocalDate() + " " +
            "\n" +
            "Sleep Quality Metrics: \n Sleep Esteem Rating: " + this.estimationSleep + "\n" +
            "Start Time: " + LocalTime.ofSecondOfDay(this.startSleepTime.toLong()).format(
        DateTimeFormatter.ofPattern("hh:mm a")
    ) +
            "\n End Time: " + LocalTime.ofSecondOfDay(this.endSleepTime.toLong()).format(
        DateTimeFormatter.ofPattern("hh:mm a")
    ) +
            "\n Sleep Features:" + "nothing special \n"
    Log.d(TAG, "[Survey text:] " + text)
    return text

}

fun Long.toLocalDate(): LocalDate {
    val instant = Instant.ofEpochMilli(this)
    return instant.atZone(ZoneId.systemDefault()).toLocalDate()
}