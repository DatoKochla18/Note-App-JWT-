package com.example.notesapp.feature.login

import com.example.notesapp.feature.login.data.di.loginRemoteModule
import com.example.notesapp.feature.login.data.di.loginRepositoryModule
import com.example.notesapp.feature.login.data.di.loginUseCaseModule
import com.example.notesapp.feature.login.presentation.di.loginViewModelModule
import org.koin.core.module.Module

fun getLoginModules(): List<Module> {
    return listOf(
        loginRemoteModule, loginRepositoryModule, loginUseCaseModule, loginViewModelModule
    )
}