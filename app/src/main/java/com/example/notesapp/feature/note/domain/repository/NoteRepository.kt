package com.example.notesapp.feature.note.domain.repository

import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.feature.note.domain.model.GetNote
import com.example.notesapp.feature.note.domain.model.GetNoteRequest

interface NoteRepository {

    suspend fun getNotes(): Resource<List<GetNote>, NetworkError>

    suspend fun getNote(id: String): Resource<GetNote, NetworkError>

    suspend fun deleteNote(id: String): Resource<Unit, NetworkError>

    suspend fun addOrUpdateNote(getNoteRequest: GetNoteRequest): Resource<Unit, NetworkError>
}