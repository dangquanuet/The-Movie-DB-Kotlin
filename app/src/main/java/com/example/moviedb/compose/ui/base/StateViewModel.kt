package com.example.moviedb.compose.ui.base

import androidx.lifecycle.ViewModel
import com.example.moviedb.data.remote.toBaseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class StateViewModel : ViewModel() {

    private val _loading by lazy { MutableStateFlow(false) }
    val loading: StateFlow<Boolean> = _loading

    private val _refreshing by lazy { MutableStateFlow(false) }
    val refreshing: StateFlow<Boolean> = _refreshing

    private val _errorEvent by lazy { MutableStateFlow<ErrorEvent?>(null) }
    val errorEvent: StateFlow<ErrorEvent?> = _errorEvent

    fun showLoading() {
        _loading.value = true
    }

    fun hideLoading() {
        _loading.value = false
    }

    fun showRefreshing() {
        _refreshing.value = true
    }

    fun hideRefreshing() {
        _refreshing.value = false
    }

    open fun doRefresh() {
    }

    protected open fun onError(e: Exception) {
        hideLoading()
        hideRefreshing()
        when (e) {
            // case no internet connection
            is UnknownHostException -> {
                _errorEvent.value = ErrorEvent.Network
            }
            is ConnectException -> {
                _errorEvent.value = ErrorEvent.Network
            }
            // case request time out
            is SocketTimeoutException -> {
                _errorEvent.value = ErrorEvent.Timeout
            }
            else -> {
                // convert throwable to base exception to get error information
                val baseException = e.toBaseException()
                when (baseException.httpCode) {
                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        _errorEvent.value = ErrorEvent.Unauthorized
                    }
                    else -> {
                        _errorEvent.value = ErrorEvent.Unknown(baseException = baseException)
                    }
                }
            }
        }
    }

    fun hideError() {
        _errorEvent.value = null
    }
}