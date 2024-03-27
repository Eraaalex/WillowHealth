package com.example.willowhealth.di

import com.example.willowhealth.presentation.authentification.LoginViewModel
import com.example.willowhealth.presentation.insights.InsightsViewModel
import com.example.willowhealth.presentation.settings.SettingsViewModel
import com.example.willowhealth.presentation.survey.SurveyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { InsightsViewModel(get()) }
    viewModel { SurveyViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { SettingsViewModel() }
}