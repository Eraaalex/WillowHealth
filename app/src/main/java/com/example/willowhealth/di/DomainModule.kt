package com.example.willowhealth.di

import com.example.willowhealth.GoogleFitReader
import com.example.willowhealth.model.HealthDataManager
import com.example.willowhealth.model.HealthDataManaging
import org.koin.dsl.module

val domainModule = module{
    single { GoogleFitReader()}
    factory<HealthDataManaging> { HealthDataManager(get(), get()) }
}