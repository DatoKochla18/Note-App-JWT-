package com.example.notesapp.feature.login.presentation.screen

import com.example.notesapp.core.domain.util.error.NetworkError

sealed interface LoginSideEffect {
    object SuccessFullLogin : LoginSideEffect
    data class ShowNetworkError(val error: NetworkError) : LoginSideEffect
}