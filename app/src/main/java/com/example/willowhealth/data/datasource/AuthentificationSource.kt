package com.example.willowhealth.data.datasource

interface AuthentificationSource {

    fun fetchData(userId: String, day: Int)
}

interface RealtimeSource {

    fun fetchSurveyData(userId: String, day: Int)
    fun saveSurveyData(userId: String, day: Int)
}