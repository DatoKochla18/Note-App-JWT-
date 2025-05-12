package com.example.notesapp.feature.register.domain.repository

import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError

interface RegisterRepository {

    suspend fun register(email: String, password: String): Resource<Unit, NetworkError>
}