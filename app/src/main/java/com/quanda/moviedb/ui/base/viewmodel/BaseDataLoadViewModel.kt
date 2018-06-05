package com.quanda.moviedb.ui.base.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.widget.Toast
import com.quanda.moviedb.App
import javax.inject.Inject

abstract class BaseDataLoadViewModel : BaseViewModel() {

    @Inject
    lateinit var app: App

    val isDataLoading = MutableLiveData<Boolean>().apply { value = false }

    init {
        App.appComponent.inject(this)
    }

    open fun onLoadFail(e: Throwable) {
        e.printStackTrace()
        showError(e)
        isDataLoading.value = false
    }

    open fun showError(e: Throwable) {
        Toast.makeText(app, e.message, Toast.LENGTH_SHORT).show()
    }
}