package com.quanda.moviedb.base.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.widget.Toast

abstract class BaseDataLoadViewModel(var context: Context) : BaseViewModel() {

    val isDataLoading = ObservableBoolean()

    open fun onLoadFail(e: Throwable) {
        showError(e)
        isDataLoading.set(false)
    }

    fun showError(e: Throwable) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }
}