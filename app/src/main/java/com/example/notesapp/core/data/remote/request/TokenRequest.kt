package com.example.notesapp.core.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val refreshToken: String,
)