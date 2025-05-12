package com.example.notesapp.feature.splash.presentation.di

import com.example.notesapp.feature.splash.presentation.screen.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashViewModelModule = module {
    viewModel { SplashViewModel(getValueFromLocalStorageUseCase = get()) }
}