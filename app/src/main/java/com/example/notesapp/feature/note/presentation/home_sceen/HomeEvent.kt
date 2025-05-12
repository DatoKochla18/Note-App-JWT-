package com.example.notesapp.feature.note.presentation.home_sceen

sealed interface HomeEvent {
    data object GetNotes : HomeEvent
    data class OnNoteClicked(val id: String) : HomeEvent
    data class DeleteNote(val id: String) : HomeEvent
}