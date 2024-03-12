package com.example.willowhealth.presentation.main

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.util.Calendar

object SharedPreferencesManager {
    private val PREFS_FILENAME = "prefs"
    private val DATE_KEY = "LAST_SURVEY_DATE"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    fun saveCurrentDate() {
        val currentDate = Calendar.getInstance().timeInMillis
        sharedPreferences.edit().putLong(DATE_KEY, currentDate).apply()
    }

    fun getSavedDate(): Long {
        return sharedPreferences.getLong(DATE_KEY, 0)
    }

    fun isSurveyCompleted(): Boolean {
        val savedDate = getSavedDate()
        Log.d(TAG, "[isSurveyCompleted] LAST TIME " + savedDate)
        if (savedDate == 0L) {
            return false
        }
        val currentTime = Calendar.getInstance().timeInMillis
        return currentTime - savedDate < 24 * 60 * 60 * 1000
    }

}