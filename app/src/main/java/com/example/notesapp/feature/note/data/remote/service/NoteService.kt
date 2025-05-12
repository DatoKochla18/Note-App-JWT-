package com.example.notesapp.feature.note.data.remote.service

import com.example.notesapp.feature.note.data.remote.request.NoteRequest
import com.example.notesapp.feature.note.data.remote.response.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NoteService {
    @POST("notes")
    suspend fun saveNote(@Body request: NoteRequest): Response<Unit>

    @GET("notes/{id}")
    suspend fun getNote(@Path("id") id: String): Response<NoteResponse>

    @GET("notes")
    suspend fun getNotes(): Response<List<NoteResponse>>

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: String): Response<Unit>
}