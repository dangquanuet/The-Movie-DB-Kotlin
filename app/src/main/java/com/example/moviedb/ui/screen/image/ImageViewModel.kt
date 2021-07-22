package com.example.moviedb.ui.screen.image

import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor() : BaseViewModel() {

    val imageUrl = SingleLiveData<String>()

}