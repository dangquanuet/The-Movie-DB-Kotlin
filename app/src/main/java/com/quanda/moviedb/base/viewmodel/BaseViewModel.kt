package com.quanda.moviedb.base.viewmodel

import android.content.Context
import com.quanda.moviedb.base.navigator.BaseNavigator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(context: Context, navigator: BaseNavigator) {

    val context = context
    val navigator = navigator
    val compoDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compoDisposable.add(disposable)
    }

    fun onActivityDestroyed() {
        compoDisposable.clear()
    }
}