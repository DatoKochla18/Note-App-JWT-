package com.example.notesapp.feature.note.presentation.mapper

import com.example.notesapp.feature.note.domain.model.GetNote
import com.example.notesapp.feature.note.presentation.model.Note


fun GetNote.toPresentation(): Note = Note(
    id,
    title,
    content,
    color,
    createdAt,
)