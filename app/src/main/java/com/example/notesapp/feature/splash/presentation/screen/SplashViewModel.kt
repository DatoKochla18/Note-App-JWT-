package com.example.notesapp.feature.splash.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.core.domain.use_case.cache.GetValueFromLocalStorageUseCase
import com.example.notesapp.core.domain.util.preference_key.PreferenceKeys
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getValueFromLocalStorageUseCase: GetValueFromLocalStorageUseCase,
) : ViewModel() {

    private val _uiEventChannel = Channel<SplashSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()


    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.GetToken -> {
                val result = getValueFromLocalStorageUseCase(PreferenceKeys.REFRESH_TOKEN_KEY, "")

                viewModelScope.launch {
                    result.collectLatest {
                        Log.d("token", it)
                        Log.d("tokenCheck", it.isEmpty().toString())

                        if (it.isEmpty()) {
                            _uiEventChannel.send(SplashSideEffect.NavigateToLoginScreen)
                        } else {
                            _uiEventChannel.send(SplashSideEffect.NavigateToHomeScreen)

                        }
                    }
                }
            }
        }
    }

    init {
        onEvent(SplashEvent.GetToken)
    }

}