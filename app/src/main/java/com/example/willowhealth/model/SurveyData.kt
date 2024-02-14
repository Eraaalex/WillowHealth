package com.example.willowhealth.model

import java.time.LocalDateTime
import java.time.LocalTime

data class SurveyData(
    val userId: String = "",
    var currentDateTime: LocalDateTime = LocalDateTime.now(),
    var startSleepTime: LocalTime = LocalTime.of(22, 0, 0),
    var endSleepTime: LocalTime = LocalTime.of(22, 0, 0),
    var estimationSleep: Int = 5,
)