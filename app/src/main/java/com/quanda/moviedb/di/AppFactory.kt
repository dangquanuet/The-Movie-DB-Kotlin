package com.quanda.moviedb.di

import android.arch.lifecycle.ViewModelProvider

class AppFactory() : ViewModelProvider.NewInstanceFactory() {

//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        val viewModel = super.create(modelClass)
//        if (viewModel is AppComponent.Injectable) {
//            viewModel.inject((App.appComponent))
//        }
//        return viewModel
//    }

}