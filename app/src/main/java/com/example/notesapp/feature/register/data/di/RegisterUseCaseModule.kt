package com.example.notesapp.feature.register.data.di

import com.example.notesapp.feature.register.domain.use_case.RegisterUseCase
import com.example.notesapp.feature.register.domain.use_case.ValidateRepeatPasswordUseCase
import org.koin.dsl.module


val registerUseCaseModule = module {
    single { ValidateRepeatPasswordUseCase() }

    single { RegisterUseCase(registerRepository = get()) }
}