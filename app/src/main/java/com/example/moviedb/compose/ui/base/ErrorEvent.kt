package com.example.moviedb.compose.ui.base

import com.example.moviedb.data.remote.BaseException

sealed class ErrorEvent(
    val type: ErrorType,
    val baseException: BaseException? = null
) {
    object Network : ErrorEvent(type = ErrorType.NETWORK)
    object Timeout : ErrorEvent(type = ErrorType.TIMEOUT)
    object Unauthorized : ErrorEvent(type = ErrorType.HTTP_UNAUTHORIZED)
    object ForceUpdate : ErrorEvent(type = ErrorType.FORCE_UPDATE)
    class Unknown(baseException: BaseException) :
        ErrorEvent(type = ErrorType.UNKNOWN, baseException = baseException)
}

enum class ErrorType {
    NETWORK,
    TIMEOUT,
    HTTP_UNAUTHORIZED,
    FORCE_UPDATE,
    UNKNOWN
}
