package com.example.willowhealth.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willowhealth.HealthMetrics
import com.example.willowhealth.model.HealthDataManaging
import kotlinx.coroutines.launch

class MainViewModel(private val healthDataManager: HealthDataManaging):ViewModel() {

    var steps  = MutableLiveData<HashMap<String, HashMap<String, HashMap<String, Int>>>>()

    fun fetchData(metric: HealthMetrics)  {
        viewModelScope.launch {
            steps.postValue( healthDataManager.getData(metric))
        }
    }
}