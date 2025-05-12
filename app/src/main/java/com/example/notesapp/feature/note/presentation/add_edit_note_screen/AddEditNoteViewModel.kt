package com.example.notesapp.feature.note.presentation.add_edit_note_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.feature.note.domain.model.GetNoteRequest
import com.example.notesapp.feature.note.domain.use_case.GetNoteUseCase
import com.example.notesapp.feature.note.domain.use_case.SaveNoteUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val getNoteByIdUseCase: GetNoteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditNoteUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEventChannel = Channel<AddEditNoteSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.OnTitleChanged -> {
                _uiState.update {
                    it.copy(
                        title = event.title,
                        saveEnabled = event.title.isNotBlank() && _uiState.value.content.isNotBlank()
                    )
                }
            }

            is AddEditNoteEvent.OnContentChanged -> {
                _uiState.update {
                    it.copy(
                        content = event.content,
                        saveEnabled = _uiState.value.title.isNotBlank() && event.content.isNotBlank()
                    )
                }
            }

            is AddEditNoteEvent.OnColorChanged -> {
                _uiState.update { it.copy(color = event.color) }
            }

            is AddEditNoteEvent.LoadNoteDetails -> {
                loadNoteDetails(event.id)
            }

            AddEditNoteEvent.OnSaveNoteClicked -> {
                saveNote()
            }
        }
    }

    private fun loadNoteDetails(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = getNoteByIdUseCase(id)) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiEventChannel.send(AddEditNoteSideEffect.ShowError(result.error))
                }

                is Resource.Success -> {
                    val note = result.data
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            id = note.id,
                            title = note.title,
                            content = note.content,
                            color = note.color,
                            saveEnabled = note.title.isNotBlank() && note.content.isNotBlank()
                        )
                    }
                }
            }
        }
    }

    private fun saveNote() {
        val currentState = _uiState.value

        if (currentState.title.isBlank() || currentState.content.isBlank()) {
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val noteRequest = GetNoteRequest(
                id = currentState.id,
                title = currentState.title,
                content = currentState.content,
                color = currentState.color
            )

            when (val result = saveNoteUseCase(noteRequest)) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiEventChannel.send(AddEditNoteSideEffect.ShowError(result.error))
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiEventChannel.send(AddEditNoteSideEffect.NavigateBack)
                }
            }
        }
    }
}