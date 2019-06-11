package com.example.moviedb.ui.screen.favoritemovie

import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.ui.base.BaseLoadMoreRefreshViewModel
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(
    private val movieDao: MovieDao
) : BaseLoadMoreRefreshViewModel<Movie>() {

    override fun loadData(page: Int) {
        viewModelScope.launch {
            try {
                onLoadSuccess(page, movieDao.getFavorite(getNumberItemPerPage(), page))
            } catch (e: Exception) {
                onLoadFail(e)
            }
        }
    }

}