package com.example.notesapp.feature.profile

import com.example.notesapp.feature.profile.data.di.profileRemoteModule
import com.example.notesapp.feature.profile.data.di.profileRepositoryModule
import com.example.notesapp.feature.profile.data.di.profileUseCaseModule
import com.example.notesapp.feature.profile.presentation.di.profileViewModelModule
import org.koin.core.module.Module


fun getProfileModules(): List<Module> {
    return listOf(
        profileRemoteModule,
        profileRepositoryModule,
        profileUseCaseModule,
        profileViewModelModule
    )
}