package com.example.willowhealth

import android.content.Context
import android.text.format.Time
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

open class HealthDataManager {

    val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1
    private var googleFitReader = GoogleFitReader()

    //дата<час,<тип, значение>>>
    suspend fun getData(
        context: Context,
        metric: HealthMetrics
    ): HashMap<String, HashMap<String, HashMap<String, Int>>> {
        return when (metric) {
            HealthMetrics.STEPS -> getStepsData(context)
            HealthMetrics.CALORIES -> getCaloriesData()
        }
    }


    private suspend fun getStepsData(context: Context): HashMap<String, HashMap<String, HashMap<String, Int>>> {
        val resultData: HashMap<String, HashMap<String, HashMap<String, Int>>> = hashMapOf()
        if (checkPermissionGoogleFit(context)) {
            val endDate = Date()
            return suspendCoroutine { continuation ->
                val endDate = Date()
                googleFitReader.getSteps(context, endDate) { steps ->
                    continuation.resume(steps)
                }
            }
        }
        return resultData
    }

    private fun checkPermissionGoogleFit(context: Context): Boolean {
        val account = GoogleSignIn.getAccountForExtension(context, GoogleFitReader.fitnessOptions)
        return (GoogleSignIn.hasPermissions(account, GoogleFitReader.fitnessOptions))
    }

    private fun getCaloriesData(): HashMap<String, HashMap<String, HashMap<String, Int>>> {

        return TODO("Provide the return value")
    }


}