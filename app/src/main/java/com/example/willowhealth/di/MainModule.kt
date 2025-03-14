package com.example.willowhealth.presentation.main.di

import android.app.Activity
import com.example.willowhealth.presentation.settings.UserSettings
import com.example.willowhealth.presentation.settings.UserSettingsImpl
import com.example.willowhealth.service.PermissionService
import com.example.willowhealth.service.PermissionServiceGoogleImpl
import org.koin.dsl.module

fun getMainModule(activity: Activity) = module {
    single<PermissionService> { PermissionServiceGoogleImpl(activity, get()) }
    single<UserSettings> { UserSettingsImpl(activity) }
}
