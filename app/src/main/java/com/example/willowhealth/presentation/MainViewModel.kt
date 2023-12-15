package com.example.willowhealth.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willowhealth.HealthMetric
import com.example.willowhealth.service.HealthDataManager
import kotlinx.coroutines.launch

class MainViewModel(private val healthDataManager: HealthDataManager):ViewModel() {

    var steps  = MutableLiveData<HashMap<String, HashMap<String, HashMap<String, Int>>>>()

    fun fetchData(metric: HealthMetric)  {
        viewModelScope.launch {
            steps.postValue( healthDataManager.getData(metric))
        }
    }
}