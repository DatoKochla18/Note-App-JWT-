package com.example.notesapp.feature.profile.data.repository

import com.example.notesapp.core.data.remote.util.ApiHelper
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.feature.profile.data.remote.request.LogOutRequest
import com.example.notesapp.feature.profile.data.remote.service.LogOutService
import com.example.notesapp.feature.profile.domain.repository.LogOutRepository

class LogOutRepositoryImpl(
    private val apiHelper: ApiHelper,
    private val logOutService: LogOutService,
) : LogOutRepository {
    override suspend fun logOut(refreshToken: String): Resource<Unit, NetworkError> {
        return apiHelper.handleNetworkRequestAsSuspend {
            logOutService.logOut(LogOutRequest(refreshToken))
        }
    }
}