package com.example.willowhealth.model

import java.time.LocalTime

data class SurveyData(
    val userId: String = "",
    var startSleepTime: LocalTime = LocalTime.of(22, 0, 0),
    var endSleepTime: LocalTime = LocalTime.of(22, 0, 0),
    var estimationSleep: Int = 5,
    var timestamp: Long = System.currentTimeMillis()
)