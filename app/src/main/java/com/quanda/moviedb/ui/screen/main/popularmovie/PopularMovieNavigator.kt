package com.quanda.moviedb.ui.screen.main.popularmovie

import com.quanda.moviedb.ui.base.navigator.BaseNavigator
import com.quanda.moviedb.data.model.Movie

interface PopularMovieNavigator : BaseNavigator {
    fun goToMovieDetail(movie: Movie)
}