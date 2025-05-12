package com.example.notesapp.feature.note.domain.use_case

import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.feature.note.domain.model.GetNote
import com.example.notesapp.feature.note.domain.repository.NoteRepository

class GetNotesUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(): Resource<List<GetNote>, NetworkError> {
        return noteRepository.getNotes()
    }
}