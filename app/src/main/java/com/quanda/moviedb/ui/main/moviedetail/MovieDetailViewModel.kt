package com.quanda.moviedb.ui.main.moviedetail

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.quanda.moviedb.MovieDBApplication
import com.quanda.moviedb.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.UserRepository
import javax.inject.Inject

class MovieDetailViewModel(application: Application,
        val movieDetailNavigator: MovieDetailNavigator) : BaseDataLoadViewModel(application) {

    class CustomFactory(val application: Application,
            val movieDetailNavigator: MovieDetailNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieDetailViewModel(application, movieDetailNavigator) as T
        }
    }

    val movie = MutableLiveData<Movie>()

    @Inject
    lateinit var userRepository: UserRepository

    init {
        (application as MovieDBApplication).appComponent.inject(this)
    }
}