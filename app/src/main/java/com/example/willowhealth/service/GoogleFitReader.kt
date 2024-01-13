package com.example.willowhealth.service

import android.content.Context
import android.util.Log
import com.example.willowhealth.getFormattingTime
import com.example.willowhealth.repository.AccountRepository
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import java.util.Date
import java.util.concurrent.TimeUnit

typealias MetricWithValue = HashMap<String, Int>
typealias DateTimeWithMetric = HashMap<String, HashMap<String, MetricWithValue>>

interface HealthReader {
    fun getSteps(
        startDate: Date,
        endDate: Date,
        callback: (DateTimeWithMetric) -> Unit
    )
}

class GoogleFitReader(
    private val context: Context,
    private val accountRepository: AccountRepository
) : HealthReader {
    companion object {
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_SLEEP_SEGMENT, FitnessOptions.ACCESS_READ)
            .build()

    }

    override fun getSteps(
        startDate: Date,
        endDate: Date,
        callback: (DateTimeWithMetric) -> Unit
    ) {
        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
            .setTimeRange(startDate.time, endDate.time, TimeUnit.MILLISECONDS)
            .bucketByTime(1, TimeUnit.DAYS)
            .build()

        Fitness.getHistoryClient(context, accountRepository.get())
            .readData(readRequest)
            .addOnSuccessListener { response ->
                val totalSteps = response.buckets
                    .asSequence()
                    .flatMap { it.dataSets }
                    .flatMap { it.dataPoints }
                    .sumOf { it.getValue(Field.FIELD_STEPS).asInt() }
                callback(
                    hashMapOf(
                        endDate.getFormattingTime("yyyy-MM-dd") to hashMapOf(
                            endDate.getFormattingTime("HH:mm") to hashMapOf(
                                "steps" to totalSteps
                            )
                        )
                    )
                )
            }
            .addOnFailureListener { e ->
                Log.d("WILLOW", "OnFailure()", e)
                callback(
                    hashMapOf(
                        endDate.date.toString() to hashMapOf(
                            endDate.time.toString() to hashMapOf(
                                "steps" to 0
                            )
                        )
                    )
                )
            }

    }
}