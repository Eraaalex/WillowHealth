package com.example.willowhealth.di

import android.util.Log
import com.example.willowhealth.presentation.MainActivity
import com.example.willowhealth.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    Log.d("MyApp", "AC Single")
    single { androidContext() }
    Log.d("MyApp", "AC Single finish")
    viewModel { MainViewModel(get()) }
}