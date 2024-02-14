package com.example.willowhealth.presentation.settings

import androidx.lifecycle.ViewModel
import com.example.willowhealth.data.datasource.FirebaseAuthDataSource

class SettingsViewModel : ViewModel() {

    fun logOut() {
        FirebaseAuthDataSource.logout()
    }
}