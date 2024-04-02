package com.example.willowhealth.repository

import android.util.Log
import com.example.willowhealth.data.datasource.RealtimeSource
import com.example.willowhealth.model.HealthMetric
import com.example.willowhealth.model.MissionData
import com.example.willowhealth.model.SurveyData
import com.example.willowhealth.presentation.main.TAG
import com.example.willowhealth.service.HealthDataManager
import com.example.willowhealth.utils.getCurrentDayOfWeek
import com.example.willowhealth.utils.toLocalDate
import java.time.DayOfWeek
import java.util.Date

interface UserRepository {
    fun getSurveyDataOrderByDayOfWeek(
        days: Int
    ): HashMap<DayOfWeek, Int>

    suspend fun getMissionData(week: Int = 1): List<MissionData>
    suspend fun saveMissionData(userData: MissionData)

    suspend fun fetchMissionsData(week: Int, index: Int)

    suspend fun getSurveyData(days: Int = 7): List<SurveyData>
    suspend fun fetchSurveysData(days: Int)
    fun saveSurveyData(userData: SurveyData)

    suspend fun fetchHealthData(
        metric: HealthMetric,
        startDate: Date,
        endDate: Date
    ): Int

    fun getUserId(): String

}

class UserRepositoryImpl(
    private val realtimeSource: RealtimeSource,
    private val healthDataManager: HealthDataManager
) : UserRepository {

    private var _surveys: MutableList<SurveyData>? = null
    private var _missions: MutableList<MissionData>? = null
    private var _steps: Int? = null
    private var _calories: Int? = null
    override suspend fun getMissionData(week: Int): List<MissionData> {
        _missions?.let {
            return it
        } ?: run {
            val missions = realtimeSource.fetchMissionData(week).toMutableList()
            _missions = missions
            return missions
        }
    }

    override suspend fun saveMissionData(userData: MissionData) {
        realtimeSource.updateMission(1, userData)
    }

    override suspend fun fetchMissionsData(week: Int, index: Int) {
        realtimeSource.updateMission(week, _missions?.get(index) ?: MissionData())
        val missions = realtimeSource.fetchMissionData(week).toMutableList()
        _missions = missions
    }

    override suspend fun fetchSurveysData(days: Int) {
        val surveys = realtimeSource.fetchSurveyData(days).toMutableList()
        _surveys = surveys
    }

    override suspend fun getSurveyData(days: Int): List<SurveyData> {
        _surveys?.let {
            if (it.size >= days) {
                return it.takeLast(days)
            } else {
                val surveys = realtimeSource.fetchSurveyData(days).toMutableList()
                _surveys = surveys
                return surveys
            }
        } ?: run {
            val surveys = realtimeSource.fetchSurveyData(days).toMutableList()
            _surveys = surveys
            return surveys
        }
    }

    override fun getUserId(): String {
        return realtimeSource.getUserId()
    }


    override fun saveSurveyData(userData: SurveyData) {
        realtimeSource.saveSurveyData(userData)
    }

    override fun getSurveyDataOrderByDayOfWeek(
        days: Int
    ): HashMap<DayOfWeek, Int> {
        val currentDayOfWeek = getCurrentDayOfWeek()
        val surveyDataMap = HashMap<DayOfWeek, Int>()

        _surveys?.let {
            for (survey in it) {
                survey.let {
                    val dayOfWeek = it.timestamp.toLocalDate().dayOfWeek
                    Log.e(TAG, "Start " + it.startSleepTime)
                    Log.e(TAG, "End " + it.endSleepTime)
                    if (dayOfWeek <= currentDayOfWeek) {
                        surveyDataMap[dayOfWeek] = (24 * 3600 - it.startSleepTime + it.endSleepTime)
                    }
                }
            }
        }

        return surveyDataMap

    }

    override suspend fun fetchHealthData(
        metric: HealthMetric,
        startDate: Date,
        endDate: Date
    ): Int {
        val value = (healthDataManager.getData(
            metric,
            startDate,
            endDate
        )).values.first().values.first().values.first()
        if (metric == HealthMetric.STEPS) {
            _steps = value
        } else {
            _calories = value
        }
        return value
    }
}