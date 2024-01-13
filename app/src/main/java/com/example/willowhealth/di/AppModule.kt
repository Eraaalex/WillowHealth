package com.example.willowhealth.di

import com.example.willowhealth.presentation.SurveyViewModel
import com.example.willowhealth.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SurveyViewModel() }
}