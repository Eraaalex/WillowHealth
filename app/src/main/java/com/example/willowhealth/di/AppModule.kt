package com.example.willowhealth.di

import com.example.willowhealth.presentation.authentification.LoginViewModel
import com.example.willowhealth.presentation.chat.ChatViewModel
import com.example.willowhealth.presentation.insights.InsightsViewModel
import com.example.willowhealth.presentation.main.MainViewModel
import com.example.willowhealth.presentation.settings.SettingsViewModel
import com.example.willowhealth.presentation.splash.SplashViewModel
import com.example.willowhealth.presentation.survey.SurveyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { InsightsViewModel(get(), get()) }
    viewModel { SurveyViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { ChatViewModel(get(), get()) }
}