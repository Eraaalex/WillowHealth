package com.example.willowhealth.presentation.splash

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.willowhealth.data.datasource.FirebaseAuthDataSource
import com.example.willowhealth.data.datasource.FirebaseRealtimeSource
import com.example.willowhealth.model.SurveyData
import com.example.willowhealth.presentation.main.TAG
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

//    private val _timeData = MutableLiveData<Pair<LocalTime, LocalTime>>()
//    val timeData: LiveData<Pair<LocalTime, LocalTime>> = _timeData


    var uiState =
        mutableStateOf(SurveyData(userId = FirebaseAuthDataSource.getCurrentUser()?.uid ?: ""))
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
            startSleepTime = timeStateForStart,
            endSleepTime = timeStateForEnd
        )
        Log.d(TAG, "onSaveClick: before sending data to firebase")
        FirebaseRealtimeSource.saveSurveyData(uiState.value)
        finish()
    }

    fun onRadioButtonClick(quality: Int) {
        uiState.value = uiState.value.copy(estimationSleep = quality)
    }

}

