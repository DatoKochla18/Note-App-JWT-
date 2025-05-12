package com.example.notesapp.core.domain.util.error

sealed class NetworkError : RootError {
    data object ConnectionError : NetworkError()
    data class HttpError(val code: Int, val body: String? = null) : NetworkError()
    data class ServerError(val exception: Throwable) : NetworkError()
    data object EmptyResponse : NetworkError()
    data object InvalidCredentials : NetworkError()
    data object UserNotFound : NetworkError()
}
