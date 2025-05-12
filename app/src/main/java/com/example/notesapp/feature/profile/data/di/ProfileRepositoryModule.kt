package com.example.notesapp.feature.profile.data.di

import com.example.notesapp.feature.profile.data.repository.LogOutRepositoryImpl
import com.example.notesapp.feature.profile.domain.repository.LogOutRepository
import org.koin.dsl.module

val profileRepositoryModule = module {
    single<LogOutRepository> { LogOutRepositoryImpl(apiHelper = get(), logOutService = get()) }
}