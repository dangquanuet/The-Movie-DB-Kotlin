package com.example.moviedb.ui.screen.oldmain

import com.example.moviedb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OldMainViewModel @Inject constructor() : BaseViewModel() {

    val currentTab = MutableStateFlow(Tab.POPULAR.position)

}