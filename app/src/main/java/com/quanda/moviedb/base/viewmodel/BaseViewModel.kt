package com.quanda.moviedb.base.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val compoDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compoDisposable.add(disposable)
    }

    fun onActivityDestroyed() {
        compoDisposable.clear()
    }
}