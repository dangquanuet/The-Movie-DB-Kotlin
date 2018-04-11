package com.quanda.moviedb.base.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel() : ViewModel() {

    val compoDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compoDisposable.add(disposable)
    }

    fun onActivityDestroyed() {
        compoDisposable.clear()
    }
}