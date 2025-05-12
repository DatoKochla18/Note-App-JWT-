package com.example.notesapp.feature.login.domain.model



data class GetLoginResponse(
    val accessToken: String,
    val refreshToken: String,
)