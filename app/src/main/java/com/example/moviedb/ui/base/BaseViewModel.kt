package com.example.moviedb.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.remote.toBaseException
import com.example.moviedb.data.repository.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

open class BaseViewModel : ViewModel() {
    val uiState = MutableStateFlow<UiState>(UiState.Success)

    @Inject
    lateinit var userRepo: UserRepository

    // exception handler for coroutine
    private val exceptionHandler by lazy {
        CoroutineExceptionHandler { context, throwable ->
            viewModelScope.launch {
                onError(throwable)
            }
        }
    }
    protected val viewModelScopeExceptionHandler by lazy { viewModelScope + exceptionHandler }

    fun showSuccess() {
        uiState.value = UiState.Success
    }

    /**
     * handle throwable when load fail
     */
    protected open suspend fun onError(throwable: Throwable) {
        val errorType: ErrorType = when (throwable) {
            // case no internet connection
            is UnknownHostException -> {
                ErrorType.NoInternetConnection
            }

            is ConnectException -> {
                ErrorType.NoInternetConnection
            }
            // case request time out
            is SocketTimeoutException -> {
                ErrorType.ConnectTimeout
            }

            else -> {
                // convert throwable to base exception to get error information
                val baseException = throwable.toBaseException()
                when (baseException.httpCode) {
                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        ErrorType.UnAuthorized
                    }

                    HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                        ErrorType.ServerMaintain
                    }

                    else -> {
                        ErrorType.UnknownError(throwable)
                    }
                }
            }
        }
        uiState.value = UiState.Error(errorType)
    }

    fun showError(throwable: Throwable) {
        uiState.value = UiState.Error(ErrorType.UnknownError(throwable))
    }

    fun showLoading() {
        uiState.value = UiState.Loading
    }

    fun isLoading() = uiState.value == UiState.Loading
}