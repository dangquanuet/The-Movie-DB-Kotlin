package com.example.moviedb.ui.base

data class UiState(
    val isLoading: Boolean = false,
    val errorType: ErrorType? = null
)

sealed class ErrorType {
    data object NoInternetConnection : ErrorType()
    data object ConnectTimeout : ErrorType()
    data object ForceUpdateApp : ErrorType()
    data object UnAuthorized : ErrorType()
    data object ServerMaintain : ErrorType()
    class UnknownError(val throwable: Throwable) : ErrorType()
}