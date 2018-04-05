package com.quanda.moviedb.base.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.widget.Toast
import com.quanda.moviedb.base.navigator.BaseNavigator

abstract class BaseDataLoadViewModel(context: Context, navigator: BaseNavigator) : BaseViewModel(
        context, navigator) {

    val isDataLoading = ObservableBoolean()

    open fun onLoadFail(e: Throwable) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        isDataLoading.set(false)
    }
}