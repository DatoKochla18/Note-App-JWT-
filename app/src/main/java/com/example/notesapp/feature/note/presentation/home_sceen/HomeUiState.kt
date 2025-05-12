package com.example.notesapp.feature.note.presentation.home_sceen

import com.example.notesapp.feature.note.presentation.model.Note

data class HomeUiState(
    val isLoading:Boolean = false,
    val notes:List<Note> = emptyList()
)