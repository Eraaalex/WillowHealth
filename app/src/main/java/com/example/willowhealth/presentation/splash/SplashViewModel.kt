package com.example.willowhealth.presentation.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.willowhealth.model.HealthMetric
import com.example.willowhealth.repository.HealthDataRepositoryImpl
import com.example.willowhealth.repository.UserRepository
import java.util.Date

class SplashViewModel(private val userRepository: UserRepository,
    ) : ViewModel() {
    val dataLoading = MutableLiveData<Boolean>()

    suspend fun loadData() {
        dataLoading.value = true
        userRepository.getMissionData(1)
        userRepository.getSurveyData(7)
        userRepository.fetchHealthData(HealthMetric.STEPS, Date(Date().time - 1000 * 3600 * 24), Date())
        userRepository.fetchHealthData(HealthMetric.CALORIES, Date(Date().time - 1000 * 3600 * 24), Date())
    }
}