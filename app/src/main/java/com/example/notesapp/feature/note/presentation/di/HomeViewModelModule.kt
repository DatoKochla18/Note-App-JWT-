package com.example.notesapp.feature.note.presentation.di

import com.example.notesapp.feature.note.presentation.add_edit_note_screen.AddEditNoteViewModel
import com.example.notesapp.feature.note.presentation.home_sceen.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModel { HomeViewModel(getNotesUseCase = get(), deleteNoteUseCase = get()) }
    viewModel { AddEditNoteViewModel(getNoteByIdUseCase = get(), saveNoteUseCase = get()) }

}