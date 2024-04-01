package com.example.willowhealth.service

import com.example.willowhealth.model.HealthMetric
import com.example.willowhealth.presentation.insights.MetricWithValue
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface HealthDataManager {
    suspend fun getData(
        metric: HealthMetric,
        startDate: Date,
        endDate: Date
    ): HashMap<String, HashMap<String, HashMap<String, Int>>>
}

open class HealthDataManagerGoogleImpl(
    private val permissionService: PermissionService,
    private val googleFitReader: HealthReader
) : HealthDataManager {

    override suspend fun getData(
        metric: HealthMetric,
        startDate: Date,
        endDate: Date
    ): HashMap<String, HashMap<String, MetricWithValue>> {
        return when (metric) {
            HealthMetric.STEPS -> getStepsData(startDate, endDate)
            HealthMetric.CALORIES -> getCaloriesData(startDate, endDate)
            else -> hashMapOf()
        }
    }

    private suspend fun getStepsData(
        startDate: Date,
        endDate: Date
    ): HashMap<String, HashMap<String, HashMap<String, Int>>> {
        val resultData: HashMap<String, HashMap<String, HashMap<String, Int>>> = hashMapOf()

        if (permissionService.checkPermission()) {
            return suspendCoroutine { continuation ->
                googleFitReader.getSteps(startDate, endDate) { steps ->
                    continuation.resume(steps)
                }
            }
        } else {
            permissionService.requestPermission()
        }

        return resultData
    }

    private suspend fun getCaloriesData(startDate: Date, endDate: Date): HashMap<String, HashMap<String, HashMap<String, Int>>> {

        val resultData: HashMap<String, HashMap<String, HashMap<String, Int>>> = hashMapOf()

        if (permissionService.checkPermission()) {
            return suspendCoroutine { continuation ->
                googleFitReader.getSteps(startDate, endDate) { calories ->
                    continuation.resume(calories)
                }
            }
        } else {
            permissionService.requestPermission()
        }

        return resultData
    }
}