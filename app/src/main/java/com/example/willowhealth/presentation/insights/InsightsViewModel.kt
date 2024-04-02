package com.example.willowhealth.presentation.insights

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willowhealth.main.TAG
import com.example.willowhealth.model.HealthMetric
import com.example.willowhealth.model.MissionData
import com.example.willowhealth.repository.UserRepository
import com.example.willowhealth.service.HealthDataManager
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.util.Date

data class TimeDuration(val hours: Int = 0, val mins: Int = 0)
typealias MetricWithValue = HashMap<String, Int>
typealias MetricValue = HashMap<String, HashMap<String, MetricWithValue>>

class InsightsViewModel(
    private val healthDataManager: HealthDataManager,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _sleepDuration = mutableStateOf<Map<DayOfWeek, Int>>(fillMapOfWeek())
    val sleepDuration: State<Map<DayOfWeek, Int>> = _sleepDuration

    private var _lastSurvey = mutableStateOf(0)
    val lastSurvey: State<Int> = _lastSurvey

    private var _steps =
        mutableStateOf<Int>(0)
    val steps: State<Int> = _steps

    private var _calories =
        mutableStateOf<Int>(0)
    val calories: State<Int> = _calories

    private var _missions = mutableStateOf<List<MissionData>>(listOf())
    val missions: State<List<MissionData>> = _missions

    init {
        fetchWeekSleepData()
        fetchDaySleepData()
        fetchData(HealthMetric.STEPS)
        getMissionData(1)
    }

    private fun fillMapOfWeek(): Map<DayOfWeek, Int> {
        return mutableMapOf(
            (DayOfWeek.MONDAY to 0),
            (DayOfWeek.TUESDAY to 0),
            (DayOfWeek.WEDNESDAY to 0),
            (DayOfWeek.THURSDAY to 0),
            (DayOfWeek.FRIDAY to 0),
            (DayOfWeek.SATURDAY to 0),
            (DayOfWeek.SUNDAY to 0),
        )

    }

    fun getMissionData(week: Int = 1) {
        viewModelScope.launch {
            try {
                Log.e(TAG, "[fetchMissionData] $_missions")
                _missions.value = userRepository.getMissionData(week)
                Log.e(TAG, "[fetchMissionData] $_missions")
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching week missions data", e)
            }
        }


    }

    private fun fetchWeekSleepData(days: Int = 7) {
        viewModelScope.launch {
            try {
                var weekSurvey =
                    userRepository.getSurveyDataOrderByDayOfWeek(days)
                weekSurvey = alignSurveyMap(weekSurvey)
                Log.e(TAG, "weekSurvey: $weekSurvey")
                _sleepDuration.value = weekSurvey
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching week sleep data", e)
            }
        }
    }

    fun fetchDaySleepData(days: Int = 2) {
        viewModelScope.launch {
            try {
                val surveys = userRepository.getSurveyData(days)
                Log.d("MyApp", "surveys: $surveys")
                val last = surveys.lastOrNull()
                _lastSurvey.value = 0
                last?.let {
                    _lastSurvey.value = (24 * 3600 - it.startSleepTime + it.endSleepTime) // TODO
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching sleep data", e)
            }
        }
    }

    private fun alignSurveyMap(surveyDataMap: HashMap<DayOfWeek, Int>): HashMap<DayOfWeek, Int> {
        for (day in DayOfWeek.entries) {
            if (!surveyDataMap.containsKey(day)) {
                surveyDataMap[day] = 0
            }
        }
        return surveyDataMap
    }


    fun fetchData(
        metric: HealthMetric,
        startDate: Date = Date(Date().time - 1000 * 3600 * 24),
        endDate: Date = Date()
    ) {
        viewModelScope.launch {
            if (metric == HealthMetric.STEPS) {
                _steps.value = (userRepository.fetchHealthData(
                    metric,
                    startDate,
                    endDate
                ))
            } else {
                _calories.value = (userRepository.fetchHealthData(
                    metric,
                    startDate,
                    endDate
                ))
            }
            Log.d("MyApp", "steps: ${_steps.value}")
        }
    }

    fun onMissionCheckedChange(number: Int, newIsChecked: Boolean) {

        viewModelScope.launch {
            try {
                val mission = missions.value[number]
                mission.isChecked = newIsChecked
                userRepository.fetchMissionsData(1, number)

            } catch (e: Exception) {
                Log.e(TAG, "Error with updating mission occurred: $e")
            }
        }
    }
}



