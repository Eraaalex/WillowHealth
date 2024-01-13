package com.example.willowhealth.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalTime

data class QuestionData(
    val questionId: String,
    val startTime: LocalTime,
    val endTime: LocalTime
)

class SurveyViewModel : ViewModel() {
    private val mutableStateFlow = MutableStateFlow(true)
    val isLoading = mutableStateFlow.asStateFlow()

    private val _timeData = MutableLiveData<Pair<LocalTime, LocalTime>>()
    val timeData: LiveData<Pair<LocalTime, LocalTime>> = _timeData

    fun updateTimeData(startTime: LocalTime, endTime: LocalTime) {
        _timeData.value = Pair(startTime, endTime)
    }

    fun finish() {
        mutableStateFlow.value = false
    }
}

