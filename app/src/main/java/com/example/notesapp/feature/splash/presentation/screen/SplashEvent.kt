package com.example.notesapp.feature.splash.presentation.screen

sealed interface SplashEvent {

    data object GetToken : SplashEvent
}