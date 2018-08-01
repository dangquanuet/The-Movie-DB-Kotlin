package com.quanda.moviedb.ui.base.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.widget.Toast
import com.quanda.moviedb.MainApplication
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import kotlinx.coroutines.experimental.Job
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var mainApplication: MainApplication

    val isDataLoading = MutableLiveData<Boolean>().apply { value = false }
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
        isDataLoading.value = false
    }

    open fun showError(e: Throwable) {
        Toast.makeText(mainApplication, e.message, Toast.LENGTH_SHORT).show()
    }

    fun onActivityDestroyed() {
        compoDisposable.clear()
        parentJob.cancel()
    }
}