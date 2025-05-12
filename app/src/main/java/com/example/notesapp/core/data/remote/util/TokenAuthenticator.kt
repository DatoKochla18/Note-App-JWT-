package com.example.notesapp.core.data.remote.util

import android.util.Log
import com.example.notesapp.core.data.remote.request.TokenRequest
import com.example.notesapp.core.data.remote.service.TokenService
import com.example.notesapp.core.domain.use_case.cache.GetValueFromLocalStorageUseCase
import com.example.notesapp.core.domain.use_case.cache.SaveValueToLocalStorageUseCase
import com.example.notesapp.core.domain.util.preference_key.PreferenceKeys
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenServiceProvider: () -> TokenService,
    private val getValue: GetValueFromLocalStorageUseCase,
    private val saveValue: SaveValueToLocalStorageUseCase,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.url.encodedPath.endsWith("/auth/refresh")) {
            return null
        }
        if (responseCount(response) > 1) return null

        val refreshToken = runBlocking {
            getValue(PreferenceKeys.REFRESH_TOKEN_KEY, "").first()
        }.takeIf { it.isNotBlank() } ?: return null

        Log.d("refreshToken", refreshToken.toString())

        val tokenService = tokenServiceProvider()

        val refreshResp = runBlocking {
            tokenService.refresh(TokenRequest(refreshToken))
        }

        if (!refreshResp.isSuccessful) return null
        val newTokens = refreshResp.body()!!

        runBlocking {
            saveValue(PreferenceKeys.ACCESS_TOKEN_KEY, newTokens.accessToken)
            saveValue(PreferenceKeys.REFRESH_TOKEN_KEY, newTokens.refreshToken)
        }

        return response.request
            .newBuilder()
            .header("Authorization", "Bearer ${newTokens.accessToken}")
            .build()
    }
}

private fun responseCount(response: Response): Int {
    var res: Response? = response
    var count = 1
    while (res?.priorResponse != null) {
        count++
        res = res.priorResponse
    }
    return count
}
