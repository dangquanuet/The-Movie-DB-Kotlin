package com.quanda.moviedb.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.quanda.moviedb.MainApplication

class AppFactory() : ViewModelProvider.NewInstanceFactory() {

//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        val viewModel = super.create(modelClass)
//        if (viewModel is AppComponent.Injectable) {
//            viewModel.inject((MainApplication.appComponent))
//        }
//        return viewModel
//    }

}