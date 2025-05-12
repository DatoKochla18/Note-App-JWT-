package com.example.notesapp.feature.note.domain.use_case

import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.feature.note.domain.model.GetNoteRequest
import com.example.notesapp.feature.note.domain.repository.NoteRepository

class SaveNoteUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(getNoteRequest: GetNoteRequest): Resource<Unit, NetworkError> {
        return noteRepository.addOrUpdateNote(getNoteRequest)
    }
}