package com.example.notesapp.feature.note.data.di

import com.example.notesapp.feature.note.domain.use_case.DeleteNoteUseCase
import com.example.notesapp.feature.note.domain.use_case.GetNoteUseCase
import com.example.notesapp.feature.note.domain.use_case.GetNotesUseCase
import com.example.notesapp.feature.note.domain.use_case.SaveNoteUseCase
import org.koin.dsl.module

val noteUseCaseModule = module {
    factory { GetNotesUseCase(get()) }
    factory { DeleteNoteUseCase(get()) }
    factory { SaveNoteUseCase(get()) }
    factory { GetNoteUseCase(get()) }
}