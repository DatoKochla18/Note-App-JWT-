package com.example.notesapp.feature.note.domain.model

data class GetNoteRequest(
    val id: String? = null,
    val title: String,
    val content: String,
    val color: Long,
)