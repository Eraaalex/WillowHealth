package com.example.willowhealth.main

import androidx.lifecycle.ViewModel
import com.example.willowhealth.data.datasource.AuthentificationSource
import com.google.firebase.auth.FirebaseUser

class MainViewModel(private val authDataSource: AuthentificationSource) : ViewModel() {

    fun getUser() : FirebaseUser?{
        return authDataSource.getCurrentUser()
    }
}