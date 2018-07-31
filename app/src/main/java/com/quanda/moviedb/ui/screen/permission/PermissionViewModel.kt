package com.quanda.moviedb.ui.screen.permission

import com.quanda.moviedb.MainApplication
import com.quanda.moviedb.data.local.pref.SharedPreferenceApi
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import javax.inject.Inject

class PermissionViewModel : BaseDataLoadViewModel() {

    @Inject
    lateinit var preferences: SharedPreferenceApi

    lateinit var navigator: PermissionNavigator

}