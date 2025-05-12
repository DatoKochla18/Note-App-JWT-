package com.example.notesapp.feature.profile.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.feature.profile.domain.use_case.LogOutUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val logOutUseCase: LogOutUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()


    private val _uiEventChannel = Channel<ProfileSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()


    fun onEvent(profileEvent: ProfileEvent) {
        when (profileEvent) {
            ProfileEvent.LogOut -> {
                _uiState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    when (val result = logOutUseCase()) {
                        is Resource.Error -> {
                            _uiState.update { it.copy(isLoading = false) }
                            _uiEventChannel.send(ProfileSideEffect.ShowError(result.error))
                        }

                        is Resource.Success -> {
                            _uiState.update { it.copy(isLoading = false) }
                            _uiEventChannel.send(ProfileSideEffect.NavigateToLoginScreen)
                        }

                    }
                }
            }
        }
    }
}