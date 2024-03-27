package com.example.willowhealth.model

import java.time.LocalTime

data class SurveyData(
    var startSleepTime: Int = LocalTime.of(22, 0, 0).toSecondOfDay(),
    var endSleepTime: Int = LocalTime.of(22, 0, 0).toSecondOfDay(),
    var estimationSleep: Int = 5,
) : UserData()
