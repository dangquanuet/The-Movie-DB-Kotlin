package com.quanda.moviedb.ui.base.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.widget.Toast

abstract class BaseDataLoadViewModel(application: Application) : BaseViewModel(application) {

    val isDataLoading = MutableLiveData<Boolean>().apply { value = false }

    open fun onLoadFail(e: Throwable) {
        showError(e)
        isDataLoading.value = false
    }

    fun showError(e: Throwable) {
        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
    }
}