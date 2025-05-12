package com.example.notesapp.feature.register.data.repository

import com.example.notesapp.core.data.remote.util.ApiHelper
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.feature.register.data.remote.request.RegisterRequest
import com.example.notesapp.feature.register.data.remote.service.RegisterService
import com.example.notesapp.feature.register.domain.repository.RegisterRepository

class RegisterRepositoryImpl(
    private val registerService: RegisterService,
    private val apiHelper: ApiHelper,
) : RegisterRepository {
    override suspend fun register(email: String, password: String): Resource<Unit, NetworkError> {
        return apiHelper.handleNetworkRequestAsSuspend {
            registerService.register(
                RegisterRequest(
                    email,
                    password
                )
            )
        }
    }
}