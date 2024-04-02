package com.example.willowhealth.presentation.settings

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.example.willowhealth.service.AccountServiceImpl

class SettingsViewModel(private val authService: AccountServiceImpl) : ViewModel() {

    fun logOut() {
        authService.logout()
    }

    fun getUserName(): String {
        return authService.getUser()?.email?.substringBefore('@') ?: "User"
    }

    fun switchNightMode(isNightMode: Boolean) {
        if (isNightMode) {
            Configuration.UI_MODE_NIGHT_NO
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Configuration.UI_MODE_NIGHT_YES
        }

    }
}