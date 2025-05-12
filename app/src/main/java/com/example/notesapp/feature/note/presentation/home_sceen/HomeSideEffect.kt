package com.example.notesapp.feature.note.presentation.home_sceen

import com.example.notesapp.core.domain.util.error.NetworkError

sealed interface HomeSideEffect {
    data class ShowError(val message: NetworkError) : HomeSideEffect
    data class NavigateToAddOrEditNoteScreen(val id: String) : HomeSideEffect
}