package com.example.notesapp.feature.register.presentation.screen

import com.example.notesapp.core.domain.util.error.EmailError
import com.example.notesapp.core.domain.util.error.PasswordError
import com.example.notesapp.feature.register.domain.util.RepeatPasswordError

data class RegisterUiState(
    val isLoading: Boolean = false,
    val emailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val repeatedPasswordError: RepeatPasswordError? = null,
    val isEmailValid: Boolean = false,
    val acceptedTerms: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isRepeatedPasswordValid: Boolean = false,
    val isValidForm: Boolean = false,
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val showPassword: Boolean = false,
)