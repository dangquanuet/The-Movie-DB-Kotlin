package com.quanda.moviedb.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.quanda.moviedb.MovieDBApplication

class AppFactory(val application: MovieDBApplication) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = super.create(modelClass)
        if (viewModel is AppComponent.Injectable) {
            viewModel.inject((application.appComponent))
        }
        return viewModel
    }

}