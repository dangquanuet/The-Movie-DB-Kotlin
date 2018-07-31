package com.quanda.moviedb.ui.screen.main

import com.quanda.moviedb.MainApplication
import com.quanda.moviedb.data.repository.impl.UserRepositoryImpl
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import javax.inject.Inject

class MainViewModel : BaseDataLoadViewModel() {

    @Inject
    lateinit var userRepository: UserRepositoryImpl

    lateinit var navigator: MainNavigator

    init {
        MainApplication.appComponent.inject(this)
    }
}