package com.quanda.moviedb.ui.screen.permission

import com.quanda.moviedb.data.local.pref.SharedPreferenceApi
import com.quanda.moviedb.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class PermissionViewModel : BaseViewModel() {

    @Inject
    lateinit var preferences: SharedPreferenceApi

    var navigator: PermissionNavigator? = null

}