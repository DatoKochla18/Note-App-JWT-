package com.example.notesapp.core.data.remote.service

import com.example.notesapp.core.data.remote.request.TokenRequest
import com.example.notesapp.core.data.remote.response.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {

    @POST("auth/refresh")
    suspend fun refresh(@Body refreshToken: TokenRequest): Response<TokenResponse>
}