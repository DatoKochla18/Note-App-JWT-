package com.example.notesapp.feature.note

import com.example.notesapp.feature.note.data.di.noteRemoteModule
import com.example.notesapp.feature.note.data.di.noteRepositoryModule
import com.example.notesapp.feature.note.data.di.noteUseCaseModule
import com.example.notesapp.feature.note.presentation.di.homeViewModelModule
import org.koin.core.module.Module


fun getHomeModules(): List<Module> = listOf(
    homeViewModelModule, noteRemoteModule,
    noteRepositoryModule, noteUseCaseModule
)