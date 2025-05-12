package com.example.notesapp.core.data.di

import com.example.notesapp.core.domain.use_case.cache.DeleteValueFromLocalStorageUseCase
import com.example.notesapp.core.domain.use_case.cache.GetValueFromLocalStorageUseCase
import com.example.notesapp.core.domain.use_case.cache.SaveValueToLocalStorageUseCase
import com.example.notesapp.core.domain.use_case.validation.ValidateEmailUseCase
import com.example.notesapp.core.domain.use_case.validation.ValidatePasswordUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { SaveValueToLocalStorageUseCase(get()) }
    factory { GetValueFromLocalStorageUseCase(get()) }
    factory { DeleteValueFromLocalStorageUseCase(get()) }

    factory { ValidateEmailUseCase() }
    factory { ValidatePasswordUseCase() }
}