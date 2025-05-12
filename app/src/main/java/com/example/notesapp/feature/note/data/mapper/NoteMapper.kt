package com.example.notesapp.feature.note.data.mapper

import com.example.notesapp.feature.note.data.remote.request.NoteRequest
import com.example.notesapp.feature.note.data.remote.response.NoteResponse
import com.example.notesapp.feature.note.domain.model.GetNote
import com.example.notesapp.feature.note.domain.model.GetNoteRequest

fun NoteResponse.toDomain() = GetNote(
    id,
    title,
    content,
    color,
    createdAt,
)

fun GetNoteRequest.toData() = NoteRequest(
    id,
    title,
    content,
    color,
)