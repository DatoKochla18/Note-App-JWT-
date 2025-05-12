package com.example.notesapp.feature.login.domain.use_case

import com.example.notesapp.core.data.remote.util.mapData
import com.example.notesapp.core.domain.use_case.cache.SaveValueToLocalStorageUseCase
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.core.domain.util.preference_key.PreferenceKeys.ACCESS_TOKEN_KEY
import com.example.notesapp.core.domain.util.preference_key.PreferenceKeys.REFRESH_TOKEN_KEY
import com.example.notesapp.feature.login.domain.repository.LoginRepository
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

class LoginUseCase(
    private val loginRepository: LoginRepository,
    private val saveValueToLocalStorageUseCase: SaveValueToLocalStorageUseCase,
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        rememberMe: Boolean,
    ): Resource<Unit, NetworkError> {
        val result = loginRepository.login(email, password)
        if (result is Resource.Success) {
            withContext(NonCancellable) {
                saveValueToLocalStorageUseCase(ACCESS_TOKEN_KEY, result.data.accessToken)
            }
        }
        if (result is Resource.Success && rememberMe) {
            withContext(NonCancellable) {
                saveValueToLocalStorageUseCase(REFRESH_TOKEN_KEY, result.data.refreshToken)
            }
        }

        //i am using mapData here to transform GetLoginResponse
        //domain model to Unit
        // because we do need that information in presentation layer
        return result.mapData { }
    }
}