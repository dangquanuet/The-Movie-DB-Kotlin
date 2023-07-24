package com.example.moviedb.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.remote.toBaseException
import com.example.moviedb.data.repository.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

open class BaseViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

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

    /**
     * handle throwable when load fail
     */
    protected fun toErrorType(throwable: Throwable): ErrorType {
        Timber.e("toErrorType: ${throwable.printStackTrace()}")
        return when (throwable) {
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
    }

    protected open fun onError(throwable: Throwable) {
        _uiState.update {
            it.copy(
                isLoading = false,
                errorType = toErrorType(throwable)
            )
        }
    }

    open fun hideError() {
        _uiState.update {
            it.copy(errorType = null)
        }
    }

    fun showLoading() {
        _uiState.update {
            it.copy(isLoading = true, errorType = null)
        }
    }

    fun hideLoading() {
        _uiState.update {
            it.copy(isLoading = false)
        }
    }

    fun isLoading() = uiState.value.isLoading
}