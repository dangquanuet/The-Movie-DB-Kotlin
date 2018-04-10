package com.quanda.moviedb.ui.main.popularmovie

import com.quanda.moviedb.base.navigator.BaseNavigator
import com.quanda.moviedb.data.model.Movie

interface PopularMovieNavigator : BaseNavigator {
    fun goToMovieDetail(movie: Movie)
}