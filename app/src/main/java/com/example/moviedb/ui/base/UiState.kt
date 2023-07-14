package com.example.moviedb.ui.base

sealed class UiState {
    object Success : UiState()
    object Loading : UiState()
    class Error(val errorType: ErrorType) : UiState()
}

sealed class ErrorType {
    object NoInternetConnection : ErrorType()
    object ConnectTimeout : ErrorType()
    object ForceUpdateApp : ErrorType()
    object UnAuthorized : ErrorType()
    object ServerMaintain : ErrorType()
    class UnknownError(val throwable: Throwable) : ErrorType()
}