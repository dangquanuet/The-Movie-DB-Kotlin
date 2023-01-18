package com.example.moviedb.ui.screen.splash

sealed class SplashViewState {
    object Idle : SplashViewState()
    object NavigateToHome : SplashViewState()
}