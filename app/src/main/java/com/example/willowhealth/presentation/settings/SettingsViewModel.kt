package com.example.willowhealth.presentation.settings

import androidx.lifecycle.ViewModel
import com.example.willowhealth.service.AccountServiceImpl

class SettingsViewModel(private val authService : AccountServiceImpl) : ViewModel() {

    fun logOut() {
        authService.logout()
    }

    fun getUserName(): String {
        return authService.getUser()?.email?.substringBefore('@') ?: "User"
    }
}