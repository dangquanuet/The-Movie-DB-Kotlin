package com.quanda.moviedb.ui.main.favoritemovie

import com.quanda.moviedb.data.model.Movie

interface FavoriteMovieNavigator {
    fun goToMovieDetailWithResult(movie: Movie)
}