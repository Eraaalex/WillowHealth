package com.example.willowhealth.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willowhealth.HealthMetric
import com.example.willowhealth.service.HealthDataManager
import kotlinx.coroutines.launch

data class TimeDuration(val hours: Int = 0, val mins: Int = 0) {

}
typealias MetricWithValue = HashMap<String, Int>
typealias StepsValue = HashMap<String, HashMap<String, MetricWithValue>>
typealias SleepValue = HashMap<String, HashMap<String, HashMap<String, TimeDuration>>>

class MainViewModel(private val healthDataManager: HealthDataManager):ViewModel() {

    var steps  = MutableLiveData<HashMap<String, HashMap<String, HashMap<String, Int>>>>()

    fun fetchData(metric: HealthMetric)  {
        viewModelScope.launch {
            steps.postValue( healthDataManager.getData(metric))
        }
    }
}