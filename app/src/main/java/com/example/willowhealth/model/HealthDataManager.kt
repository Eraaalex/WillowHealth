package com.example.willowhealth.model

import android.app.Activity
import android.content.Context
import com.example.willowhealth.GoogleFitReader
import com.example.willowhealth.HealthMetrics
import com.google.android.gms.auth.api.signin.GoogleSignIn
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

open class HealthDataManager(val context : Context, val googleFitReader : GoogleFitReader) : HealthDataManaging {

    val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1


    override suspend fun getData(
        metric: HealthMetrics
    ): HashMap<String, HashMap<String, HashMap<String, Int>>> {
        return when (metric) {
            HealthMetrics.STEPS -> getStepsData()
            HealthMetrics.CALORIES -> getCaloriesData()
        }
    }


    private suspend fun getStepsData(): HashMap<String, HashMap<String, HashMap<String, Int>>> {
        val resultData: HashMap<String, HashMap<String, HashMap<String, Int>>> = hashMapOf()
        if (checkPermissionGoogleFit(context)) {
            return suspendCoroutine { continuation ->
                val endDate = Date()
                googleFitReader.getSteps(context, endDate) { steps ->
                    continuation.resume(steps)
                }
            }
        } else {
            requestGoogleFitPermission(context)
        }
        return resultData
    }

    private fun checkPermissionGoogleFit(context: Context): Boolean {
        val account = GoogleSignIn.getAccountForExtension(this.context, GoogleFitReader.fitnessOptions)
        return (GoogleSignIn.hasPermissions(account, GoogleFitReader.fitnessOptions))
    }

    private fun getCaloriesData(): HashMap<String, HashMap<String, HashMap<String, Int>>> {

        return TODO("Provide the return value")
    }
    private fun requestGoogleFitPermission(context : Context){
        val account = GoogleSignIn.getAccountForExtension(context,
            GoogleFitReader.fitnessOptions
        )
        GoogleSignIn.requestPermissions(
            context as Activity,
            GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
            account,
            GoogleFitReader.fitnessOptions
        )
    }



}