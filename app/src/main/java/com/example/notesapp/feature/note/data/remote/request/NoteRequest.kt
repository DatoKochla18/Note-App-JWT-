package com.example.notesapp.feature.note.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val id: String? = null,
    val title: String,
    val content: String,
    val color: Long,
)