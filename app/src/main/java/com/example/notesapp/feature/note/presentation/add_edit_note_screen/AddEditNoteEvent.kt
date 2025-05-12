package com.example.notesapp.feature.note.presentation.add_edit_note_screen

sealed interface AddEditNoteEvent {
    data class OnTitleChanged(val title: String) : AddEditNoteEvent
    data class OnContentChanged(val content: String) : AddEditNoteEvent
    data class OnColorChanged(val color: Long) : AddEditNoteEvent
    data object OnSaveNoteClicked : AddEditNoteEvent
    data class LoadNoteDetails(val id: String) : AddEditNoteEvent
}