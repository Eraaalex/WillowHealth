package com.example.willowhealth.di

import com.example.willowhealth.presentation.authentification.LoginViewModel
import com.example.willowhealth.presentation.main.MainViewModel
import com.example.willowhealth.presentation.splash.SurveyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SurveyViewModel() }
    viewModel { LoginViewModel() }
//    viewModelOf(::MainViewModel)
}