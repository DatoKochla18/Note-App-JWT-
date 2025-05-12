package com.example.notesapp.feature.register.data.remote.service

import com.example.notesapp.feature.register.data.remote.request.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<Unit>

}