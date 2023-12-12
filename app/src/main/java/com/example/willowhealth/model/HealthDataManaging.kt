package com.example.willowhealth.model

import com.example.willowhealth.HealthMetrics

interface HealthDataManaging {
    suspend fun getData(
        metric: HealthMetrics
    ): HashMap<String, HashMap<String, HashMap<String, Int>>> ;

}