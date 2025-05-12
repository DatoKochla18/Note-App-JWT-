package com.example.notesapp.feature.login.data.repository

import com.example.notesapp.core.data.remote.util.ApiHelper
import com.example.notesapp.core.data.remote.util.mapData
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.feature.login.data.mapper.toDomain
import com.example.notesapp.feature.login.data.remote.request.LoginRequest
import com.example.notesapp.feature.login.data.remote.service.LoginService
import com.example.notesapp.feature.login.domain.model.GetLoginResponse
import com.example.notesapp.feature.login.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val apiHelper: ApiHelper,
    private val loginService: LoginService,
) : LoginRepository {
    override suspend fun login(
        email: String,
        password: String,
    ): Resource<GetLoginResponse, NetworkError> {
        return apiHelper.handleNetworkRequestAsSuspend {
            loginService.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            )
        }.mapData { it.toDomain() }
    }

}