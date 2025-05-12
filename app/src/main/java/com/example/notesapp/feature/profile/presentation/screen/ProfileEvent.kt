package com.example.notesapp.feature.profile.presentation.screen

sealed interface ProfileEvent {
    data object LogOut : ProfileEvent
}