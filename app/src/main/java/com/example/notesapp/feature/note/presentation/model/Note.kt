package com.example.notesapp.feature.note.presentation.model

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val color: Long,
    val createdAt: String,
)