package com.example.willowhealth.presentation.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.willowhealth.data.datasource.FirebaseRealtimeSource
import com.example.willowhealth.model.SurveyData
import com.example.willowhealth.presentation.main.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalTime

class SurveyViewModel : ViewModel() {
    private val mutableStateFlow = MutableStateFlow(true)
    val isLoading = mutableStateFlow.asStateFlow()

    var uiState =
        mutableStateOf(SurveyData())
        private set

    private val endSleepTime
        get() = uiState.value.endSleepTime
    private val startSleepTime
        get() = uiState.value.startSleepTime
    private val estimationSleep
        get() = uiState.value.estimationSleep


    fun finish() {
        mutableStateFlow.value = false
    }

    fun onSaveClick(timeStateForStart: LocalTime, timeStateForEnd: LocalTime) {

        uiState.value = uiState.value.copy(
            startSleepTime = timeStateForStart.toSecondOfDay(),
            endSleepTime = timeStateForEnd.toSecondOfDay(),
        )
        FirebaseRealtimeSource.saveSurveyData(uiState.value, "Surveys")
        SharedPreferencesManager.saveCurrentDate()
        finish()
    }


    fun onRadioButtonClick(quality: Int) {
        uiState.value = uiState.value.copy(estimationSleep = quality)
    }

}

