package com.example.notesapp.feature.register.data.di

import com.example.notesapp.feature.register.data.repository.RegisterRepositoryImpl
import com.example.notesapp.feature.register.domain.repository.RegisterRepository
import org.koin.dsl.module


val registerRepositoryModule = module {
    single<RegisterRepository> {
        RegisterRepositoryImpl(
            apiHelper = get(),
            registerService = get()
        )
    }
}