package com.example.moviedb.ui.screen.image

import com.example.moviedb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor() : BaseViewModel() {

    val imageUrl by lazy { MutableStateFlow("") }

}