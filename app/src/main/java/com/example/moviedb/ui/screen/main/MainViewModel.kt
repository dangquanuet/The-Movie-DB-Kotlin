package com.example.moviedb.ui.screen.main

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    val currentTab = MutableLiveData<Int>()

}