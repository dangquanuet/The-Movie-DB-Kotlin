package com.quanda.moviedb.ui.screen.permission

import com.quanda.moviedb.App
import com.quanda.moviedb.data.local.SharedPreferenceApi
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import javax.inject.Inject

class PermissionViewModel : BaseDataLoadViewModel() {

    @Inject
    lateinit var preferences: SharedPreferenceApi

    lateinit var navigator: PermissionNavigator

    init {
        App.appComponent.inject(this)
    }
}