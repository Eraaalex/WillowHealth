package com.example.willowhealth.service

import android.content.Context
import com.example.willowhealth.HealthMetric
import com.example.willowhealth.repository.AccountRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface HealthDataManager {
    suspend fun getData(
        metric: HealthMetric
    ): HashMap<String, HashMap<String, HashMap<String, Int>>>
}

open class HealthDataManagerGoogleImpl(
    val permissionService: PermissionService,
    val googleFitReader : HealthReader
) : HealthDataManager {

    override suspend fun getData(
        metric: HealthMetric
    ): HashMap<String, HashMap<String, HashMap<String, Int>>> {
        return when (metric) {
            HealthMetric.STEPS -> getStepsData()
            HealthMetric.CALORIES -> getCaloriesData()
        }
    }


    private suspend fun getStepsData(): HashMap<String, HashMap<String, HashMap<String, Int>>> {
        val resultData: HashMap<String, HashMap<String, HashMap<String, Int>>> = hashMapOf()

        if (permissionService.checkPermission()) {
            return suspendCoroutine { continuation ->
                val endDate = Date()
                googleFitReader.getSteps(endDate) { steps ->
                    continuation.resume(steps)
                }
            }
        } else {
            permissionService.requestPermission()
        }

        return resultData
    }

    private fun getCaloriesData(): HashMap<String, HashMap<String, HashMap<String, Int>>> {

        return TODO("Provide the return value")
    }
}