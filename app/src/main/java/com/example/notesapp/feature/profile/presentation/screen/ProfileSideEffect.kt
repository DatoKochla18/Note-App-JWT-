package com.example.notesapp.feature.profile.presentation.screen

import com.example.notesapp.core.domain.util.error.NetworkError

sealed interface ProfileSideEffect {

    data class ShowError(val message: NetworkError) : ProfileSideEffect
    data object NavigateToLoginScreen : ProfileSideEffect
}