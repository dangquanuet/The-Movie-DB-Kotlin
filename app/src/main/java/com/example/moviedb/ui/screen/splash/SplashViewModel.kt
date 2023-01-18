package com.example.moviedb.ui.screen.splash

import androidx.lifecycle.viewModelScope
import com.example.moviedb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    private val _splashViewStateFlow = MutableStateFlow<SplashViewState>(SplashViewState.Idle)
    val splashViewStateFlow = _splashViewStateFlow.asStateFlow()

    fun showSplash() {
        viewModelScope.launch {
            delay(1000)
            _splashViewStateFlow.emit(SplashViewState.NavigateToHome)
        }
    }
}