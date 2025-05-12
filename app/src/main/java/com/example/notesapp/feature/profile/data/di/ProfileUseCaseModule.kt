package com.example.notesapp.feature.profile.data.di

import com.example.notesapp.feature.profile.domain.use_case.LogOutUseCase
import org.koin.dsl.module


val profileUseCaseModule = module {
    factory {
        LogOutUseCase(
            logOutRepository = get(),
            getValueFromLocalStorageUseCase = get(),
            deleteValueFromLocalStorageUseCase = get()
        )
    }
}