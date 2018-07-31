package com.quanda.moviedb.ui.screen.main

import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(

) : BaseDataLoadViewModel() {

    var navigator: MainActivityNavigator? = null

}