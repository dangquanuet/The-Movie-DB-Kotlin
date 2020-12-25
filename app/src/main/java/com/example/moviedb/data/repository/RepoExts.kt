package com.example.moviedb.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            Result.Error(throwable)
        }
    }
}
