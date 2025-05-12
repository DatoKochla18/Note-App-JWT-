package com.example.notesapp.feature.register

import com.example.notesapp.feature.register.data.di.registerRemoteModule
import com.example.notesapp.feature.register.data.di.registerRepositoryModule
import com.example.notesapp.feature.register.data.di.registerUseCaseModule
import com.example.notesapp.feature.register.presentation.di.registerViewModelModule
import org.koin.core.module.Module

fun getRegisterModule(): List<Module> {
    return listOf(
        registerRemoteModule, registerRepositoryModule, registerUseCaseModule,
        registerViewModelModule
    )
}