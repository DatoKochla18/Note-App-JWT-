package com.example.notesapp.feature.login.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.core.domain.use_case.validation.ValidateEmailUseCase
import com.example.notesapp.core.domain.use_case.validation.ValidatePasswordUseCase
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.feature.login.domain.use_case.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEventChannel = Channel<LoginSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(event.email, event.password)

            LoginEvent.SwitchCheckBoxStatus -> updateState(_uiState.value.copy(rememberMe = !_uiState.value.rememberMe))

            LoginEvent.SwitchShowPasswordStatus ->
                updateState(_uiState.value.copy(showPassword = !_uiState.value.showPassword))

            is LoginEvent.OnEmailChanged -> {
                updateState(_uiState.value.copy(email = event.email))
                viewModelScope.launch {
                    when (val result = validateEmailUseCase(event.email)) {
                        is Resource.Error ->
                            updateState(uiState.value.copy(emailError = result.error))

                        is Resource.Success -> updateState(
                            _uiState.value.copy(
                                emailError = null,
                                isEmailValid = true,
                                isValidForm = _uiState.value.isPasswordValid
                            )
                        )

                    }
                }
            }

            is LoginEvent.OnPasswordChanged -> {
                updateState(_uiState.value.copy(password = event.password))
                viewModelScope.launch {
                    when (val result = validatePasswordUseCase(event.password)) {
                        is Resource.Error -> updateState(_uiState.value.copy(passwordError = result.error))
                        is Resource.Success -> updateState(
                            _uiState.value.copy(
                                passwordError = null,
                                isPasswordValid = true,
                                isValidForm = uiState.value.isEmailValid
                            )
                        )

                    }
                }
            }
        }
    }


    private fun login(email: String, password: String) {
        updateState(_uiState.value.copy(isLoading = true))
        viewModelScope.launch {
            when (val result = loginUseCase(email, password, _uiState.value.rememberMe)) {
                is Resource.Error -> {
                    updateState(uiState.value.copy(isLoading = false))

                    _uiEventChannel.send(LoginSideEffect.ShowNetworkError(result.error))
                }

                is Resource.Success -> {
                    updateState(_uiState.value.copy(isLoading = false))
                    _uiEventChannel.send(LoginSideEffect.SuccessFullLogin)
                }
            }
        }

    }


    private fun updateState(newState: LoginUiState) {
        _uiState.update {
            it.copy(
                isLoading = newState.isLoading,
                emailError = newState.emailError,
                passwordError = newState.passwordError,
                isEmailValid = newState.isEmailValid,
                isPasswordValid = newState.isPasswordValid,
                isValidForm = newState.isValidForm,
                email = newState.email,
                password = newState.password,
                rememberMe = newState.rememberMe,
                showPassword = newState.showPassword,
            )
        }
    }
}