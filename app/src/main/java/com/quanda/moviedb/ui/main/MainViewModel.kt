package com.quanda.moviedb.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.quanda.moviedb.base.viewmodel.BaseDataLoadViewModel

class MainViewModel(context: Context, var mainNavigator: MainNavigator) : BaseDataLoadViewModel(
        context) {

    class CustomFactory(val context: Context,
            val mainNavigator: MainNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(context, mainNavigator) as T
        }
    }

}