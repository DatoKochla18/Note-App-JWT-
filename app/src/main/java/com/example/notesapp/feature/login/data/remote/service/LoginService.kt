package com.example.notesapp.feature.login.data.remote.service

import com.example.notesapp.feature.login.data.remote.request.LoginRequest
import com.example.notesapp.feature.login.data.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

}