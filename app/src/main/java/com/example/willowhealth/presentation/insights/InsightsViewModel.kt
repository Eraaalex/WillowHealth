package com.example.willowhealth.presentation.insights

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willowhealth.data.datasource.FirebaseRealtimeSource
import com.example.willowhealth.main.TAG
import com.example.willowhealth.model.HealthMetric
import com.example.willowhealth.model.MissionData
import com.example.willowhealth.service.HealthDataManager
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.util.Date

data class TimeDuration(val hours: Int = 0, val mins: Int = 0)
typealias MetricWithValue = HashMap<String, Int>
typealias StepsValue = HashMap<String, HashMap<String, MetricWithValue>>
typealias SleepValue = HashMap<String, HashMap<String, HashMap<String, TimeDuration>>>

class InsightsViewModel(private val healthDataManager: HealthDataManager) : ViewModel() {

    private var _sleepDuration = mutableStateOf<Map<DayOfWeek, Int>>(fillMapOfWeek())
    val sleepDuration: State<Map<DayOfWeek, Int>> = _sleepDuration

    private var _lastSurvey = mutableStateOf(0)
    val lastSurvey: State<Int> = _lastSurvey

    private var _steps =
        mutableStateOf<Int>(0)
    val steps: State<Int> = _steps

    private var _missions = mutableStateOf<List<MissionData>>(listOf())
    val missions: State<List<MissionData>> = _missions

    init {
        fetchWeekSleepData()
        fetchDaySleepData()
        fetchData(HealthMetric.STEPS)
        fetchMissionData(1)
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

    fun fetchMissionData(week: Int = 1) {
        viewModelScope.launch {
            try {
                Log.e(TAG, "[fetchMissionData] $_missions")
                _missions.value = FirebaseRealtimeSource.fetchMissionsForWeek(week)
                Log.e(TAG, "[fetchMissionData] $_missions")
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching week missions data", e)
            }
        }


    }

    fun fetchWeekSleepData(days: Long = 7) {
        viewModelScope.launch {
            try {
                var weekSurvey =
                    FirebaseRealtimeSource.getSurveyDataUntilCurrent(days)
                weekSurvey = alignSurveyMap(weekSurvey)
                Log.e(TAG, "weekSurvey: $weekSurvey")
                _sleepDuration.value = weekSurvey
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching week sleep data", e)
            }
        }
    }

    fun fetchDaySleepData(days: Long = 2) {
        viewModelScope.launch {
            try {
                val surveys = FirebaseRealtimeSource.getSurveyDataUntilCurrent(days)
                Log.d("MyApp", "surveys: $surveys")
                _lastSurvey.value = surveys.values.lastOrNull() ?: 0
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
        startDate: Date = Date(Date().time - 1000 * 60 * 60 * 24),
        endDate: Date = Date()
    ) {
        viewModelScope.launch {
            _steps.value = (healthDataManager.getData(
                metric,
                startDate,
                endDate
            )).values.first().values.first().values.first()
            Log.d("MyApp", "steps: ${_steps.value}")
        }
    }

    fun onMissionCheckedChange(number: Int, newIsChecked: Boolean) {
        viewModelScope.launch {
            try {
                val mission = missions.value[number]
                mission.isChecked = newIsChecked
                FirebaseRealtimeSource.updateMission(mission)

            } catch (e: Exception) {
                Log.e(TAG, "Error with updating mission occurred: $e")
            }
        }
    }
}



