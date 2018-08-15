package com.quanda.moviedb.ui.base.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import kotlinx.coroutines.experimental.Job
import kotlin.coroutines.experimental.CoroutineContext

abstract class BaseViewModel(

) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val errorMessage = MutableLiveData<String>()

    // rx
    val compoDisposable = CompositeDisposable()

    // coroutines
    val parentJob = Job()
    val exceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun addDisposable(disposable: Disposable) {
        compoDisposable.add(disposable)
    }

    open fun onLoadFail(e: Throwable) {
        e.printStackTrace()
        showError(e)
        isLoading.value = false
    }

    open fun showError(e: Throwable) {
//        Toast.makeText(mainApplication, e.message, Toast.LENGTH_SHORT).show()
    }

    fun onActivityDestroyed() {
        compoDisposable.clear()
        parentJob.cancel()
    }
}