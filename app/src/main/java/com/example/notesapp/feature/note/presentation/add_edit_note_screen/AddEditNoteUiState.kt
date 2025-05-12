package com.example.notesapp.feature.note.presentation.add_edit_note_screen

data class AddEditNoteUiState(
    val id: String? = null,
    val title: String = "",
    val content: String = "",
    val color: Long = 0xFF5C6BC0,
    val isLoading: Boolean = false,
    val saveEnabled: Boolean = false,
)
