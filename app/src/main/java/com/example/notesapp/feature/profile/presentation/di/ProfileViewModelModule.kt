package com.example.notesapp.feature.profile.presentation.di

import com.example.notesapp.feature.profile.presentation.screen.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileViewModelModule = module {
    viewModel {
        ProfileViewModel(logOutUseCase = get())
    }
}