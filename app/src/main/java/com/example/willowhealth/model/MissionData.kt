package com.example.willowhealth.model

data class MissionData(
    val missionShort: String = "",
    val number: Int = 0,
    var isChecked: Boolean = false,
) : FirebaseData()


