package com.example.willowhealth.repository

import com.example.willowhealth.model.HealthMetric
import com.example.willowhealth.service.HealthDataManager
import java.util.Date

interface HealthDataRepository {
    suspend fun getCalories(startDate: Date, endDate: Date): Int
    suspend fun getSteps(startDate: Date, endDate: Date): Int
}

class HealthDataRepositoryImpl(private val healthDataManager: HealthDataManager) :
    HealthDataRepository {
    private var _calories: Int? = null
    private var _steps: Int? = null

    private suspend fun getData(
        metric: HealthMetric,
        startDate: Date,
        endDate: Date
    ): Int {
        val value = (healthDataManager.getData(
            metric,
            startDate,
            endDate
        )).values.first().values.first().values.first()
        return value
    }

    override suspend fun getCalories(
        startDate: Date,
        endDate: Date
    ): Int {
        val calories = getData(
            HealthMetric.CALORIES,
            startDate,
            endDate,
        )
        _calories = calories
        return _calories ?: 0
    }

    override suspend fun getSteps(
        startDate: Date,
        endDate: Date
    ): Int {
        val steps = getData(
            HealthMetric.STEPS,
            startDate,
            endDate,
        )
        _steps = steps
        return _steps ?: 0
    }

}


