package com.example.willowhealth.model

data class MissionData(
    val missionShort : String = "",
    val isCompleted : Boolean = false,
    ) : UserData()

open class UserData(val timestamp : Long = System.currentTimeMillis())