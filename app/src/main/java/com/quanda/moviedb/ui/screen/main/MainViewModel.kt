package com.quanda.moviedb.ui.screen.main

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.quanda.moviedb.MainApplication
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.data.repository.impl.UserRepository
import javax.inject.Inject

class MainViewModel(application: Application,
        var mainNavigator: MainNavigator) : BaseDataLoadViewModel(
        application) {

    class CustomFactory(val application: Application,
            val mainNavigator: MainNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(application, mainNavigator) as T
        }
    }

    @Inject
    lateinit var userRepository: UserRepository

    init {
        MainApplication.appComponent.inject(this)
    }
}