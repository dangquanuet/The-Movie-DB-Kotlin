package com.example.moviedb.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.remote.toBaseException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {

    // loading flag
    val isLoading by lazy { MutableStateFlow(false) }

    // error message
    val errorMessage by lazy { MutableSharedFlow<String>() }

    // optional flags
    val noInternetConnectionEvent by lazy { MutableSharedFlow<Unit>() }
    val connectTimeoutEvent by lazy { MutableSharedFlow<Unit>() }
    val forceUpdateAppEvent by lazy { MutableSharedFlow<Unit>() }
    val serverMaintainEvent by lazy { MutableSharedFlow<Unit>() }
    val unknownErrorEvent by lazy { MutableSharedFlow<Unit>() }

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
    protected open suspend fun onError(throwable: Throwable) {
        when (throwable) {
            // case no internet connection
            is UnknownHostException -> {
                noInternetConnectionEvent.emit(Unit)
            }

            is ConnectException -> {
                noInternetConnectionEvent.emit(Unit)
            }
            // case request time out
            is SocketTimeoutException -> {
                connectTimeoutEvent.emit(Unit)
            }

            else -> {
                // convert throwable to base exception to get error information
                val baseException = throwable.toBaseException()
                when (baseException.httpCode) {
                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        errorMessage.emit(baseException.message ?: "")
                    }

                    HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                        errorMessage.emit(baseException.message ?: "")
                    }

                    else -> {
                        unknownErrorEvent.emit(Unit)
                    }
                }
            }
        }
        hideLoading()
    }

    open suspend fun showError(e: Throwable) {
        errorMessage.emit(e.message ?: "")
    }

    fun showLoading() {
        isLoading.value = true
    }

    fun hideLoading() {
        isLoading.value = false
    }
}
