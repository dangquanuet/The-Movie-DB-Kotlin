package com.example.moviedb.ui.screen.image

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.ui.base.BaseViewModel

class ImageViewModel @ViewModelInject constructor() : BaseViewModel() {

    val imageUrl = MutableLiveData<String>()

}