package com.example.notesapp.feature.note.presentation.add_edit_note_screen

import com.example.notesapp.core.domain.util.error.NetworkError

sealed interface AddEditNoteSideEffect {
    data class ShowError(val message: NetworkError) : AddEditNoteSideEffect
    data object NavigateBack : AddEditNoteSideEffect
}