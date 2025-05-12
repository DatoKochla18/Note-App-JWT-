package com.example.notesapp.feature.note.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class NoteResponse(
    val id: String,
    val title: String,
    val content: String,
    val color: Long,
    val createdAt: String,
)