package com.example.willowhealth.model

data class MissionData(
    val missionShort: String = "",
    val number: Int? = null,
    var isChecked: Boolean = false,
)


open class UserData(val timestamp: Long = System.currentTimeMillis())