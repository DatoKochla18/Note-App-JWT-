package com.example.notesapp.core.data.remote.util

import com.example.notesapp.core.domain.use_case.cache.GetValueFromLocalStorageUseCase
import com.example.notesapp.core.domain.util.preference_key.PreferenceKeys
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val getValue: GetValueFromLocalStorageUseCase
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            getValue(PreferenceKeys.ACCESS_TOKEN_KEY, "").first()
        }
        val reqBuilder = chain.request().newBuilder()
        if (accessToken.isNotBlank()) {
            reqBuilder.header("Authorization", "Bearer $accessToken")
        }
        return chain.proceed(reqBuilder.build())
    }
}