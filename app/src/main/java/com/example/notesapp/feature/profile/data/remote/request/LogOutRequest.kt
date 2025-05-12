package com.example.notesapp.feature.profile.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class LogOutRequest(
    val refreshToken: String,
)