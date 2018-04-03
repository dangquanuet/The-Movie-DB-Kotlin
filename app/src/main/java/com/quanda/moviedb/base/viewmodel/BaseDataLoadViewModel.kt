package com.quanda.moviedb.base.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import com.quanda.moviedb.base.navigator.BaseNavigator

abstract class BaseDataLoadViewModel(context: Context, navigator: BaseNavigator) : BaseViewModel(
        context, navigator) {

    val isDataLoading = ObservableBoolean()

    fun onLoadFail() {
        //TODO handle error
        isDataLoading.set(false)
    }
}