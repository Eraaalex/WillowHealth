package com.example.willowhealth

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest

import java.util.Date
import java.util.concurrent.TimeUnit

public class GoogleFitReader() {

    companion object {
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_SLEEP_SEGMENT, FitnessOptions.ACCESS_READ)
            .build()

    }

    fun getSteps(
        context: Context,
        endDate: Date,
        callback: (HashMap<String, HashMap<String, HashMap<String, Int>>>) -> Unit
    ) {
        val account = GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        val startDate = Date(endDate.time - 3600000 * 5)
        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
            .setTimeRange(startDate.time, endDate.time, TimeUnit.MILLISECONDS)
            .bucketByTime(1, TimeUnit.DAYS)
            .build()

        Fitness.getHistoryClient(context, account)
            .readData(readRequest)
            .addOnSuccessListener { response ->
                val totalSteps = response.buckets
                    .flatMap { it.dataSets }
                    .flatMap { it.dataPoints }
                    .sumOf { it.getValue(Field.FIELD_STEPS).asInt() }
                callback(
                    hashMapOf(
                        endDate.date.toString() to hashMapOf(
                            endDate.time.toString() to hashMapOf(
                                "steps" to totalSteps
                            )
                        )
                    )
                )
            }
            .addOnFailureListener { e ->
                Log.d("WILLOW", "OnFailure()", e)
                callback(hashMapOf(
                    endDate.date.toString() to hashMapOf(
                        endDate.time.toString() to hashMapOf(
                            "steps" to 0
                        )
                    )
                )) // or handle the error appropriately
            }

    }
}