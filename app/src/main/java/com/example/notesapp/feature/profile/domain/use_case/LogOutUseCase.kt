package com.example.notesapp.feature.profile.domain.use_case

import com.example.notesapp.core.domain.use_case.cache.DeleteValueFromLocalStorageUseCase
import com.example.notesapp.core.domain.use_case.cache.GetValueFromLocalStorageUseCase
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.core.domain.util.preference_key.PreferenceKeys
import com.example.notesapp.feature.profile.domain.repository.LogOutRepository
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class LogOutUseCase(
    private val logOutRepository: LogOutRepository,
    private val getValueFromLocalStorageUseCase: GetValueFromLocalStorageUseCase,
    private val deleteValueFromLocalStorageUseCase: DeleteValueFromLocalStorageUseCase,
) {
    suspend operator fun invoke(): Resource<Unit, NetworkError> {
        val refreshToken =
            getValueFromLocalStorageUseCase(PreferenceKeys.REFRESH_TOKEN_KEY, "").first()

        val result = logOutRepository.logOut(refreshToken = refreshToken)

        if (result is Resource.Success) {
            withContext(NonCancellable) {
                deleteValueFromLocalStorageUseCase(PreferenceKeys.REFRESH_TOKEN_KEY)
                deleteValueFromLocalStorageUseCase(PreferenceKeys.ACCESS_TOKEN_KEY)
            }
        }
        return result

    }
}