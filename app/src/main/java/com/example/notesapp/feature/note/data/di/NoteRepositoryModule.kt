package com.example.notesapp.feature.note.data.di

import com.example.notesapp.feature.note.data.repository.NoteRepositoryImpl
import com.example.notesapp.feature.note.domain.repository.NoteRepository
import org.koin.dsl.module

val noteRepositoryModule = module {
    single<NoteRepository> { NoteRepositoryImpl(apiHelper = get(), noteService = get()) }
}