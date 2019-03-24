package com.example.moviedb.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.data.remote.BaseException
import com.example.moviedb.utils.SingleLiveEvent
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val errorMessage = MutableLiveData<String>()

    val noInternetConnectionEvent = SingleLiveEvent<Unit>()
    val connectTimeoutEvent = SingleLiveEvent<Unit>()
    val forceUpdateAppEvent = SingleLiveEvent<Unit>()
    val serverMaintainEvent = SingleLiveEvent<Unit>()

    // rx
//    private val compositeDisposable = CompositeDisposable()
//    fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    // coroutines
    private val viewModelJob = Job()
    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        uiScope.launch {
            onLoadFail(throwable)
        }
    }
    protected val ioContext = viewModelJob + Dispatchers.IO
    protected val uiContext = viewModelJob + Dispatchers.Main
    protected val ioScope = CoroutineScope(ioContext)
    protected val uiScope = CoroutineScope(uiContext)

    open suspend fun onLoadFail(throwable: Throwable) {
        withContext(uiContext) {
            when (throwable.cause) {
                is UnknownHostException -> {
                    noInternetConnectionEvent.call()
                }
                is SocketTimeoutException -> {
                    connectTimeoutEvent.call()
                }
                else -> {
                    when (throwable) {
                        is BaseException -> {
                            when (throwable.httpCode) {
                                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                                    errorMessage.value = throwable.message
                                }
                                HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                                    errorMessage.value = throwable.message
                                }
                                else -> {
                                    errorMessage.value = throwable.message
                                }
                            }
                        }
                        else -> {
                            errorMessage.value = throwable.message
                        }
                    }
                }
            }
            isLoading.value = false
        }
    }

    open fun showError(e: Throwable) {
        errorMessage.value = e.message
    }

    fun showLoading() {
        isLoading.value = true
    }

    fun hideLoading() {
        isLoading.value = false
    }

    fun onDestroy() {
//        compositeDisposable.clear()
        viewModelJob.cancel()
    }
}