package com.example.willowhealth

open class HealthDataManager() {
//дата<час,<тип, значение>>>
    fun getData(metric: HealthMetrics) :HashMap<String,HashMap<String, HashMap<String, Int>> > {
        return when (metric){
            HealthMetrics.STEPS -> getStepsData()
            HealthMetrics.CALORIES -> getCaloriesData()
        }
    }



    private fun getStepsData(): HashMap<String, HashMap<String, HashMap<String, Int>>> {

        val resultData: HashMap<String, HashMap<String, HashMap<String, Int>>> = hashMapOf()


        return resultData
    }

    private fun getCaloriesData(): HashMap<String, HashMap<String, HashMap<String, Int>>> {

        return TODO("Provide the return value")
    }


}