package com.quanda.moviedb.ui.screen

import com.quanda.moviedb.data.repository.UserRepository
import com.quanda.moviedb.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel : BaseViewModel() {

    @Inject
    lateinit var userRepository: UserRepository

    var navigator: MainActivityNavigator? = null

}