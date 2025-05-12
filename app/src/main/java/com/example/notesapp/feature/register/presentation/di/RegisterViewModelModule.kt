package com.example.notesapp.feature.register.presentation.di

import com.example.notesapp.feature.register.presentation.screen.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val registerViewModelModule = module {
    viewModel {
        RegisterViewModel(
            registerUseCase = get(),
            validateEmailUseCase = get(),
            validatePasswordUseCase = get(),
            validateRepeatPasswordUseCase = get()
        )
    }
}