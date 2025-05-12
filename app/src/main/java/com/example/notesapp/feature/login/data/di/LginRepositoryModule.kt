package com.example.notesapp.feature.login.data.di

import com.example.notesapp.feature.login.data.repository.LoginRepositoryImpl
import com.example.notesapp.feature.login.domain.repository.LoginRepository
import org.koin.dsl.module


val loginRepositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(apiHelper = get(), loginService = get()) }
}