package com.example.notesapp.feature.login.domain.repository

import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.feature.login.domain.model.GetLoginResponse

interface LoginRepository {

    suspend fun login(email: String, password: String): Resource<GetLoginResponse, NetworkError>
}