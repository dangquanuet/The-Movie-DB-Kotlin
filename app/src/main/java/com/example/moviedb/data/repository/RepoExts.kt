package com.example.moviedb.data.repository

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.success(apiCall.invoke())
        } catch (throwable: Throwable) {
            if (throwable is CancellationException) {
                throw throwable
            } else {
                Result.failure(throwable)
            }
        }
    }
}
