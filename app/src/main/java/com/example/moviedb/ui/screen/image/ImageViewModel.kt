package com.example.moviedb.ui.screen.image

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.ui.base.BaseViewModel

class ImageViewModel : BaseViewModel() {

    val imageUrl = MutableLiveData<String>()

}