package com.example.willowhealth.presentation.survey

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willowhealth.model.MissionData
import com.example.willowhealth.model.SurveyData
import com.example.willowhealth.presentation.main.SharedPreferencesManager
import com.example.willowhealth.presentation.main.TAG
import com.example.willowhealth.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime

class SurveyViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val mutableStateFlow = MutableStateFlow(true)
    val isLoading = mutableStateFlow.asStateFlow()

    var uiState =
        mutableStateOf(SurveyData())
        private set

    var missions = mutableStateOf<List<MissionData>>(listOf())
        private set


    fun finish() {
        mutableStateFlow.value = false
    }

    fun fetchMissionData(week: Int = 1) {
        viewModelScope.launch {
            try {
                missions.value = userRepository.getMissionData(week)
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching week missions data", e)
            }
        }
    }

    fun updateMissionTemporaryCheckedState(missionNumber: Int, isChecked: Boolean) {
        val updatedMissions = missions.value.map {
            if (it.number == missionNumber) {
                it.copy(isChecked = isChecked)
            } else {
                it
            }
        }
        missions.value = updatedMissions
    }

    fun onSaveClick(timeStateForStart: LocalTime, timeStateForEnd: LocalTime) {

        uiState.value = uiState.value.copy(
            startSleepTime = timeStateForStart.toSecondOfDay(),
            endSleepTime = timeStateForEnd.toSecondOfDay(),
        )
        userRepository.saveSurveyData(uiState.value)
//        FirebaseRealtimeSource.saveSurveyData(uiState.value)
        SharedPreferencesManager.saveCurrentDate()
        finish()
    }


    fun onRadioButtonClick(quality: Int) {
        uiState.value = uiState.value.copy(estimationSleep = quality)
    }

}

