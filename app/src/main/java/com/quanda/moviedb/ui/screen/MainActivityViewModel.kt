package com.quanda.moviedb.ui.screen

import com.quanda.moviedb.data.repository.UserRepository
import com.quanda.moviedb.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(

) : BaseViewModel() {

    var navigator: MainActivityNavigator? = null

}