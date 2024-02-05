package com.example.willowhealth.di

import com.example.willowhealth.repository.AccountRepository
import com.example.willowhealth.repository.AccountRepositoryImpl
import com.example.willowhealth.service.GoogleFitReader
import com.example.willowhealth.service.HealthDataManager
import com.example.willowhealth.service.HealthDataManagerGoogleImpl
import com.example.willowhealth.service.HealthReader
import org.koin.dsl.module

val serviceModule = module {
    single<HealthReader> { GoogleFitReader(get(), get()) }
    single<HealthDataManager> { HealthDataManagerGoogleImpl(get(), get()) }
}

val repositoryModule = module {

//    singleOf(::AccountRepositoryImpl) { bind<AccountRepositoryImpl>() }
    single<AccountRepository> { AccountRepositoryImpl(get()) }
}
