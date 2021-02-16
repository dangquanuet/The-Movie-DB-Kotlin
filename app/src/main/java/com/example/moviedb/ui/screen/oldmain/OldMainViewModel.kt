package com.example.moviedb.ui.screen.oldmain

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OldMainViewModel @Inject constructor() : BaseViewModel() {

    val currentTab = MutableLiveData<Int>()

}