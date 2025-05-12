package com.example.notesapp.feature.login.data.di

import com.example.notesapp.feature.login.data.remote.service.LoginService
import org.koin.dsl.module
import retrofit2.Retrofit


val loginRemoteModule = module {
    single<LoginService> { get<Retrofit>().create(LoginService::class.java) }

}