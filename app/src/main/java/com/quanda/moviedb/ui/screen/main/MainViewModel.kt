package com.quanda.moviedb.ui.screen.main

import com.quanda.moviedb.App
import com.quanda.moviedb.data.repository.impl.UserRepository
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import javax.inject.Inject

class MainViewModel : BaseDataLoadViewModel() {

    @Inject
    lateinit var userRepository: UserRepository

    lateinit var navigator: MainNavigator

    init {
        App.appComponent.inject(this)
    }
}