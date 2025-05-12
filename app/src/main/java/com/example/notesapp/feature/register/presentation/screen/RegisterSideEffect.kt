package com.example.notesapp.feature.register.presentation.screen

import com.example.notesapp.core.domain.util.error.NetworkError

sealed interface RegisterSideEffect {
    data object NavigateToLoginScreen : RegisterSideEffect
    data class ShowError(val error: NetworkError) : RegisterSideEffect
}