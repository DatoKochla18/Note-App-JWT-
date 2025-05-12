package com.example.notesapp.feature.profile.domain.repository

import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError

interface LogOutRepository {
    suspend fun logOut(refreshToken: String): Resource<Unit, NetworkError>
}