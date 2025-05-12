package com.example.notesapp.feature.register.data.di

import com.example.notesapp.feature.register.data.remote.service.RegisterService
import org.koin.dsl.module
import retrofit2.Retrofit

val registerRemoteModule = module {
    single<RegisterService> {
        get<Retrofit>().create(RegisterService::class.java)
    }
}