package com.quanda.moviedb.ui.main.moviedetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.quanda.moviedb.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.UserRepository

class MovieDetailViewModel(context: Context,
        var movieDetailNavigator: MovieDetailNavigator) : BaseDataLoadViewModel(context) {

    class CustomFactory(val context: Context,
            val movieDetailNavigator: MovieDetailNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieDetailViewModel(context, movieDetailNavigator) as T
        }
    }

    val movie = MutableLiveData<Movie>()
    val userRepository = UserRepository.getInstance(context)

}