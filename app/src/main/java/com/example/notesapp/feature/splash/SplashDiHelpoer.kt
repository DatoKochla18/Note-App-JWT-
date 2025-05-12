package com.example.notesapp.feature.splash

import com.example.notesapp.feature.splash.presentation.di.splashViewModelModule
import org.koin.core.module.Module


fun getSplashModules(): List<Module> {
    return listOf(splashViewModelModule)
}