package com.example.moviedb.ui.screen.moviedetail

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(
    private val movieRepository: MovieRepository,
    private val movieDao: MovieDao
) : BaseViewModel() {

    val movie = MutableLiveData<Movie>()
    val favoriteChanged = MutableLiveData<Boolean>().apply { value = false }

    fun favoriteMovie() {
        val newMovie = movie.value
        newMovie?.isFavorite = movie.value?.isFavorite != true
        movie.value = newMovie

        favoriteChanged.value = true

        newMovie?.let {
            ioScope.launch {
                try {
                    movieRepository.updateDB(it)
                } catch (e: Exception) {
                    withContext(uiContext) {
                        onLoadFail(e)
                    }
                }
            }
        }
    }
}