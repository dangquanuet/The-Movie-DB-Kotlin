package com.example.moviedb.data.repository

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()

    data class Error(val throwable: Throwable) : Result<Nothing>()
}

inline fun <T : Any> Result<T>.then(
    success: (value: T) -> Unit,
    error: (throwable: Throwable) -> Unit
) {
    when (this) {
        is Result.Success -> {
            success.invoke(data)
        }
        is Result.Error -> {
            error.invoke(throwable)
        }
    }
}
