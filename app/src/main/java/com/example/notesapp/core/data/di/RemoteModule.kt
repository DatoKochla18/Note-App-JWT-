package com.example.notesapp.core.data.di

import com.example.notesapp.core.data.remote.service.TokenService
import com.example.notesapp.core.data.remote.util.ApiHelper
import com.example.notesapp.core.data.remote.util.AuthInterceptor
import com.example.notesapp.core.data.remote.util.TokenAuthenticator
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val remoteModule = module {
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }

    single { AuthInterceptor(get()) }

    single(named("apiClient")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<AuthInterceptor>())
            .authenticator(get<TokenAuthenticator>())
            .build()
    }
    single { Json { ignoreUnknownKeys = true } }

    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.7:8085/")
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .client(get(named("apiClient")))
            .build()
    }

    single<TokenService> { get<Retrofit>().create(TokenService::class.java) }

    single {
        TokenAuthenticator(
            tokenServiceProvider = { get() },
            getValue = get(),
            saveValue = get()
        )
    }

    single {
        get<OkHttpClient.Builder>()
            .authenticator(get<TokenAuthenticator>())
            .build()
    }

    single { ApiHelper() }
}