package com.example.notesapp.core.data.remote.util

import android.util.Log
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.core.domain.util.Resource
import com.example.notesapp.core.domain.util.error.RootError
import okio.IOException
import retrofit2.Response

class ApiHelper {
    suspend fun <T> handleNetworkRequestAsSuspend(
        apiCall: suspend () -> Response<T>,
    ): Resource<T, NetworkError> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    Resource.Success(data)
                } ?: Resource.Success(Unit as T)
            } else {
                val error = when (response.code()) {
                    401 -> NetworkError.InvalidCredentials
                    404 -> NetworkError.UserNotFound
                    else -> NetworkError.HttpError(response.code(), response.errorBody()?.string())
                }
                Resource.Error(error)
            }
        } catch (e: IOException) {
            Log.d("error",e.toString())
            Resource.Error(NetworkError.ConnectionError)
        } catch (e: Exception) {
            Log.d("error",e.toString())
            Resource.Error(NetworkError.ServerError(e))
        }
    }
}

fun <T, R, E : RootError> Resource<T, E>.mapData(transform: (T) -> R): Resource<R, E> =
    when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(error)
    }