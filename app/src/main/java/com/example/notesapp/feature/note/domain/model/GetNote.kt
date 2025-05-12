package com.example.notesapp.feature.note.domain.model

data class GetNote(
    val id: String,
    val title: String,
    val content: String,
    val color: Long,
    val createdAt: String,
)