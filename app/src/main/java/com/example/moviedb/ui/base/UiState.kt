package com.example.moviedb.ui.base

data class UiState(
    val isLoading: Boolean = false,
    val errorType: ErrorType? = null
)

sealed class ErrorType {
    object NoInternetConnection : ErrorType()
    object ConnectTimeout : ErrorType()
    object ForceUpdateApp : ErrorType()
    object UnAuthorized : ErrorType()
    object ServerMaintain : ErrorType()
    class UnknownError(val throwable: Throwable) : ErrorType()
}