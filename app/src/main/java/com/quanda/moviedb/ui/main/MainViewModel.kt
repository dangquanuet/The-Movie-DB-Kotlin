package com.quanda.moviedb.ui.main

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.quanda.moviedb.base.viewmodel.BaseDataLoadViewModel

class MainViewModel(application: Application,
        var mainNavigator: MainNavigator) : BaseDataLoadViewModel(
        application) {

    class CustomFactory(val application: Application,
            val mainNavigator: MainNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(application, mainNavigator) as T
        }
    }

}