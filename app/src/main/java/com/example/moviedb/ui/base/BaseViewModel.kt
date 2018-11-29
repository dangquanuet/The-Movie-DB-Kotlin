package com.example.moviedb.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.data.remote.BaseException
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import kotlinx.coroutines.experimental.Job
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.experimental.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val errorMessage = MutableLiveData<String>()

    // rx
    val compositeDisposable = CompositeDisposable()

    // coroutines
    val parentJob = Job()
    val exceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    open fun onLoadFail(throwable: Throwable) {
        try {
            when (throwable) {
                is BaseException -> {
                    when (throwable.serverErrorCode) {
                        // custom server error code
                        else -> {
                            when (throwable.cause) {
                                is UnknownHostException -> {
                                    errorMessage.value = "No Internet Connection"
                                }
                                is SocketTimeoutException -> {
                                    errorMessage.value = "Connect timeout, please retry"
                                }
                                else -> {
                                    errorMessage.value = throwable.message
                                }
                            }
                        }
                    }
                }
                else -> {
                    errorMessage.value = throwable.message
                }
            }
        } catch (e: Exception) {
            errorMessage.value = throwable.message
        }
        isLoading.value = false
    }

    open fun showError(e: Throwable) {
        errorMessage.value = e.message
    }

    fun onActivityDestroyed() {
        compositeDisposable.clear()
        parentJob.cancel()
    }
}