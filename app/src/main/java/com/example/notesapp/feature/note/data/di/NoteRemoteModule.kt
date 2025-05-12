package com.example.notesapp.feature.note.data.di

import com.example.notesapp.feature.note.data.remote.service.NoteService
import org.koin.dsl.module
import retrofit2.Retrofit

val noteRemoteModule = module {
    single<NoteService> { get<Retrofit>().create(NoteService::class.java) }
}