package com.example.notesapp.feature.register.presentation.screen

sealed interface RegisterEvent {
    data class Register(val email: String, val password: String) :
        RegisterEvent

    data class OnEmailChanged(val email: String) : RegisterEvent
    data class OnPasswordChanged(val password: String) : RegisterEvent
    data class OnRepeatedPasswordChanged(val repeatedPassword: String) :
        RegisterEvent

    data object OnShowPasswordChanged : RegisterEvent
    data object OnTermsAcceptedChanged : RegisterEvent

}