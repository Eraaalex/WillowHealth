package com.example.willowhealth.app

import android.app.Application
import android.util.Log
import com.example.willowhealth.di.appModule
import com.example.willowhealth.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WillowHealth : Application(){
    override fun onCreate() {
        super.onCreate()
        Log.d("MyApp", "Before Koin initialization")
        startKoin {
            androidLogger(Level.DEBUG)
            Log.d("MyApp", "Before androidContext initialization")
            androidContext(this@WillowHealth)
            Log.d("MyApp", "After androidContext initialization")
            modules(listOf(appModule, domainModule))
        }
        Log.d("MyApp", "After Koin initialization")

    }
}