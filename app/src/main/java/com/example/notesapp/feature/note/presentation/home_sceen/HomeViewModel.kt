package com.example.notesapp.feature.note.presentation.home_sceen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.feature.note.domain.use_case.DeleteNoteUseCase
import com.example.notesapp.feature.note.domain.use_case.GetNotesUseCase
import com.example.notesapp.feature.note.presentation.mapper.toPresentation
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEventChannel = Channel<HomeSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()



    fun onEvent(homeEvent: HomeEvent) {
        when (homeEvent) {
            HomeEvent.GetNotes -> {
                getNotes()
            }

            is HomeEvent.OnNoteClicked -> {
                viewModelScope.launch {
                    _uiEventChannel.send(HomeSideEffect.NavigateToAddOrEditNoteScreen(homeEvent.id))
                }
            }

            is HomeEvent.DeleteNote -> {
                println("DeleteNote event received for note: ${homeEvent.id}")
                deleteNote(homeEvent.id)
            }
        }
    }

    private fun getNotes() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = getNotesUseCase()) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiEventChannel.send(HomeSideEffect.ShowError(result.error))
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            notes = result.data.map { it.toPresentation() })
                    }

                }
            }
        }
    }

    private fun deleteNote(id: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = deleteNoteUseCase(id)) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiEventChannel.send(HomeSideEffect.ShowError(result.error))
                }

                is Resource.Success -> {
                    getNotes()

                }
            }
        }
    }

}