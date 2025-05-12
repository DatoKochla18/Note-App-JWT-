package com.example.notesapp.feature.note.data.repository

import com.example.notesapp.core.data.remote.util.ApiHelper
import com.example.notesapp.core.data.remote.util.mapData
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.feature.note.data.mapper.toData
import com.example.notesapp.feature.note.data.mapper.toDomain
import com.example.notesapp.feature.note.data.remote.service.NoteService
import com.example.notesapp.feature.note.domain.model.GetNote
import com.example.notesapp.feature.note.domain.model.GetNoteRequest
import com.example.notesapp.feature.note.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val apiHelper: ApiHelper,
    private val noteService: NoteService,
) : NoteRepository {
    override suspend fun getNotes(): Resource<List<GetNote>, NetworkError> {
        return apiHelper.handleNetworkRequestAsSuspend { noteService.getNotes() }
            .mapData { it.map { it.toDomain() } }
    }

    override suspend fun getNote(id: String): Resource<GetNote, NetworkError> {
        return apiHelper.handleNetworkRequestAsSuspend { noteService.getNote(id) }
            .mapData { it.toDomain() }
    }

    override suspend fun deleteNote(id: String): Resource<Unit, NetworkError> {
        return apiHelper.handleNetworkRequestAsSuspend { noteService.deleteNote(id) }

    }

    override suspend fun addOrUpdateNote(getNoteRequest: GetNoteRequest): Resource<Unit, NetworkError> {
        return apiHelper.handleNetworkRequestAsSuspend { noteService.saveNote(getNoteRequest.toData()) }
    }
}