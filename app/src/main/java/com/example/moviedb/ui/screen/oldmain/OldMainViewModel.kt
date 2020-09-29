package com.example.moviedb.ui.screen.oldmain

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.ui.base.BaseViewModel

class OldMainViewModel @ViewModelInject constructor(): BaseViewModel() {

    val currentTab = MutableLiveData<Int>()

}