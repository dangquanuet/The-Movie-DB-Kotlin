package com.quanda.moviedb.ui.screen.popularmovie

import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.ui.base.navigator.BaseNavigator

interface PopularMovieNavigator : BaseNavigator {
    fun goToMovieDetail(movie: Movie)
}