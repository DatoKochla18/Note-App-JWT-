package com.example.notesapp.feature.login.presentation.screen

import com.example.notesapp.core.domain.util.error.EmailError
import com.example.notesapp.core.domain.util.error.PasswordError

data class LoginUiState(
    val isLoading: Boolean = false,
    val emailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isValidForm: Boolean = false,
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val showPassword: Boolean = false,
)
