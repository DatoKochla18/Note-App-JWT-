package com.example.notesapp.feature.profile.data.di

import com.example.notesapp.feature.profile.data.remote.service.LogOutService
import org.koin.dsl.module
import retrofit2.Retrofit

val profileRemoteModule = module {
    single<LogOutService> { get<Retrofit>().create(LogOutService::class.java) }
}