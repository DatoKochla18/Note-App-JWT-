package com.example.notesapp.feature.register.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.core.domain.use_case.validation.ValidateEmailUseCase
import com.example.notesapp.core.domain.use_case.validation.ValidatePasswordUseCase
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.feature.register.domain.use_case.RegisterUseCase
import com.example.notesapp.feature.register.domain.use_case.ValidateRepeatPasswordUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateRepeatPasswordUseCase: ValidateRepeatPasswordUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEventChannel = Channel<RegisterSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> register(
                event.email,
                event.password
            )

            is RegisterEvent.OnEmailChanged -> {
                emailChanged(event.email)
            }

            is RegisterEvent.OnPasswordChanged -> {
                passwordChanged(event.password)
            }

            is RegisterEvent.OnRepeatedPasswordChanged -> {
                repeatPasswordChanged(_uiState.value.password, event.repeatedPassword)
            }

            RegisterEvent.OnShowPasswordChanged -> {
                updateState(_uiState.value.copy(showPassword = !_uiState.value.showPassword))
            }

            RegisterEvent.OnTermsAcceptedChanged -> {
                _uiState.update { it.copy(acceptedTerms = !_uiState.value.acceptedTerms) }
            }
        }
    }

    private fun emailChanged(email: String) {
        when (val result = validateEmailUseCase(email)) {
            is Resource.Error ->
                updateState(uiState.value.copy(email = email, emailError = result.error))

            is Resource.Success -> updateState(
                _uiState.value.copy(
                    email = email,
                    emailError = null,
                    isEmailValid = true,
                    isValidForm = _uiState.value.isPasswordValid && _uiState.value.isRepeatedPasswordValid
                )
            )
        }
    }

    private fun passwordChanged(password: String) {
        when (val result = validatePasswordUseCase(password)) {
            is Resource.Error ->
                updateState(uiState.value.copy(password = password, passwordError = result.error))

            is Resource.Success -> updateState(
                _uiState.value.copy(
                    password = password,
                    passwordError = null,
                    isPasswordValid = true,
                    isValidForm = _uiState.value.isEmailValid && _uiState.value.isRepeatedPasswordValid
                )
            )
        }
    }

    private fun repeatPasswordChanged(password: String, repeatPassword: String) {
        when (val result =
            validateRepeatPasswordUseCase(password = password, repeatedPassword = repeatPassword)) {
            is Resource.Error ->
                updateState(
                    uiState.value.copy(
                        repeatPassword = repeatPassword,
                        repeatedPasswordError = result.error
                    )
                )

            is Resource.Success -> updateState(
                _uiState.value.copy(
                    repeatPassword = repeatPassword,
                    repeatedPasswordError = null,
                    isRepeatedPasswordValid = true,
                    isValidForm = _uiState.value.isEmailValid && _uiState.value.isPasswordValid
                )
            )
        }
    }

    private fun register(email: String, password: String) {
        updateState(_uiState.value.copy(isLoading = true))
        viewModelScope.launch {
            when (val result = registerUseCase(email, password)) {
                is Resource.Error -> {
                    updateState(_uiState.value.copy(isLoading = false))

                    _uiEventChannel.send(RegisterSideEffect.ShowError(result.error))
                }

                is Resource.Success -> {
                    _uiEventChannel.send(
                        RegisterSideEffect.NavigateToLoginScreen
                    )
                }
            }

        }
    }

    private fun updateState(newState: RegisterUiState) {
        _uiState.update {
            it.copy(
                isLoading = newState.isLoading,
                emailError = newState.emailError,
                passwordError = newState.passwordError,
                isEmailValid = newState.isEmailValid,
                repeatPassword = newState.repeatPassword,
                repeatedPasswordError = newState.repeatedPasswordError,
                isPasswordValid = newState.isPasswordValid,
                isRepeatedPasswordValid = newState.isRepeatedPasswordValid,
                isValidForm = newState.isValidForm,
                email = newState.email,
                password = newState.password,
                showPassword = newState.showPassword,
            )
        }
    }
}