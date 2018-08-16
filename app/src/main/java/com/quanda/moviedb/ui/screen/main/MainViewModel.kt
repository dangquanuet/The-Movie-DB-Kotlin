package com.quanda.moviedb.ui.screen.main

import com.quanda.moviedb.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
) : BaseViewModel() {

    var navigator: MainNavigator? = null

}