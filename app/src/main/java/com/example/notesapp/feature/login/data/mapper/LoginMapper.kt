package com.example.notesapp.feature.login.data.mapper

import com.example.notesapp.feature.login.data.remote.response.LoginResponse
import com.example.notesapp.feature.login.domain.model.GetLoginResponse


fun LoginResponse.toDomain() =
    GetLoginResponse(accessToken = accessToken, refreshToken = refreshToken)