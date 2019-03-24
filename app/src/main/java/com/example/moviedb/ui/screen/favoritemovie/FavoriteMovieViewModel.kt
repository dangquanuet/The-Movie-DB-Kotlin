package com.example.moviedb.ui.screen.favoritemovie

import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.ui.base.BaseLoadMoreRefreshViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteMovieViewModel(
    private val movieDao: MovieDao
) : BaseLoadMoreRefreshViewModel<Movie>() {

    override fun loadData(page: Int) {
        ioScope.launch {
            try {
                val movieList = movieDao.getFavorite(getNumberItemPerPage(), page)
                withContext(uiContext) {
                    onLoadSuccess(page, movieList)
                }
            } catch (e: Exception) {
                onLoadFailUI(e)
            }
        }
    }

}