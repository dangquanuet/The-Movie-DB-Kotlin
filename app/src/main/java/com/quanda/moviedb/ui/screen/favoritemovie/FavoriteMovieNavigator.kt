package com.quanda.moviedb.ui.screen.favoritemovie

import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.ui.base.navigator.BaseNavigator

interface FavoriteMovieNavigator : BaseNavigator {
    fun goToMovieDetailWithResult(movie: Movie)
}