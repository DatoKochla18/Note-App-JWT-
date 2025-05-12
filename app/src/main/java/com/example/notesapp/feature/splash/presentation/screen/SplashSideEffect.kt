package com.example.notesapp.feature.splash.presentation.screen

sealed interface SplashSideEffect {

    data object NavigateToHomeScreen : SplashSideEffect
    data object NavigateToLoginScreen : SplashSideEffect
}