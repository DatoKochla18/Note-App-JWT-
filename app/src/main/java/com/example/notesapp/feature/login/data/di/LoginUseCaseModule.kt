package com.example.notesapp.feature.login.data.di

import com.example.notesapp.feature.login.domain.use_case.LoginUseCase
import org.koin.dsl.module

val loginUseCaseModule = module {
    factory { LoginUseCase(loginRepository = get(), saveValueToLocalStorageUseCase = get()) }
}