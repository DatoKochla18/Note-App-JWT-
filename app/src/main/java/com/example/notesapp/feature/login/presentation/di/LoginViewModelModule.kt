package com.example.notesapp.feature.login.presentation.di

import com.example.notesapp.feature.login.presentation.screen.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginViewModelModule = module {
    viewModel {
        LoginViewModel(
            validateEmailUseCase = get(),
            validatePasswordUseCase = get(),
            loginUseCase = get()
        )
    }
}