package com.example.willowhealth.di

import com.example.willowhealth.data.datasource.AuthentificationSource
import com.example.willowhealth.data.datasource.FirebaseAuthentificationSourceImpl
import com.example.willowhealth.data.datasource.RealtimeSource
import com.example.willowhealth.data.datasource.FirebaseRealtimeSourceImpl
import com.example.willowhealth.repository.AccountRepository
import com.example.willowhealth.repository.AccountRepositoryImpl
import com.example.willowhealth.repository.UserRepository
import com.example.willowhealth.repository.UserRepositoryImpl
import com.example.willowhealth.service.AccountServiceImpl
import com.example.willowhealth.service.GoogleFitReader
import com.example.willowhealth.service.HealthDataManager
import com.example.willowhealth.service.HealthDataManagerGoogleImpl
import com.example.willowhealth.service.HealthReader
import org.koin.dsl.module

val serviceModule = module {
    single<HealthReader> { GoogleFitReader(get(), get()) }
    single<HealthDataManager> { HealthDataManagerGoogleImpl(get(), get()) }
    single<AccountServiceImpl> { AccountServiceImpl(get()) }
}

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<AccountRepository> { AccountRepositoryImpl(get()) }
}


val dataModule = module {
    single<AuthentificationSource> { FirebaseAuthentificationSourceImpl() }
    single<RealtimeSource> { FirebaseRealtimeSourceImpl(get()) }
}