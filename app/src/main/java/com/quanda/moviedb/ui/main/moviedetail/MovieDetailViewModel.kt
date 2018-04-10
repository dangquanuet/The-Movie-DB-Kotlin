package com.quanda.moviedb.ui.main.moviedetail

import android.content.Context
import android.databinding.ObservableField
import com.quanda.moviedb.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.UserRepository

class MovieDetailViewModel(context: Context,
        movieDetailNavigator: MovieDetailNavigator) : BaseDataLoadViewModel(context,
        movieDetailNavigator) {

    val movie = ObservableField<Movie>()
    val userRepository = UserRepository.getInstance()

    fun getMovieDetail() {

    }

}