package com.example.willowhealth.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.willowhealth.TimeData
import java.time.LocalTime

data class QuestionData(
    val questionId: String,
    val startTime: LocalTime,
    val endTime: LocalTime
)

class SurveyViewModel : ViewModel() {
    private val _timeData = MutableLiveData<Pair<LocalTime, LocalTime>>()
    val timeData: LiveData<Pair<LocalTime, LocalTime>> = _timeData

    fun updateTimeData(startTime: LocalTime, endTime: LocalTime) {
        _timeData.value = Pair(startTime, endTime)
    }
}

