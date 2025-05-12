package com.example.notesapp.feature.profile.data.remote.service

import com.example.notesapp.feature.profile.data.remote.request.LogOutRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LogOutService {
    @POST("auth/logout")
    suspend fun logOut(@Body refreshToken: LogOutRequest): Response<Unit>
}