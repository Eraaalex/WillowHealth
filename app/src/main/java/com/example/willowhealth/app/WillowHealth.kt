package com.example.willowhealth.app

import android.app.Application
import android.util.Log
import com.example.willowhealth.di.appModule
import com.example.willowhealth.di.repositoryModule
import com.example.willowhealth.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WillowHealth : Application() {

    lateinit var koinApp: KoinApplication

    override fun onCreate() {
        super.onCreate()
        Log.d("MyApp", "Before Koin initialization")

        koinApp = startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@WillowHealth)
            modules(listOf(appModule, serviceModule, repositoryModule))
        }

        Log.d("MyApp", "After Koin initialization")

    }
}