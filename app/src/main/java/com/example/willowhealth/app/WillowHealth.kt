package com.example.willowhealth.app

import android.app.Application
import com.example.willowhealth.di.appModule
import com.example.willowhealth.di.dataModule
import com.example.willowhealth.di.repositoryModule
import com.example.willowhealth.di.serviceModule
import com.example.willowhealth.presentation.main.SharedPreferencesManager
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WillowHealth : Application() {

    lateinit var koinApp: KoinApplication

    override fun onCreate() {
        super.onCreate()

        koinApp = startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@WillowHealth)
            modules(listOf(appModule, serviceModule, repositoryModule, dataModule))
        }
        FirebaseApp.initializeApp(this)
        SharedPreferencesManager.init(this)

    }
}