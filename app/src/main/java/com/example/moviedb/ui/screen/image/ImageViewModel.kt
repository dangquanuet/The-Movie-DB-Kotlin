package com.example.moviedb.ui.screen.image

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor() : BaseViewModel() {

    val imageUrl = MutableLiveData<String>()

}