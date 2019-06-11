package com.example.moviedb.ui.screen.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(
    val movieRepository: MovieRepository,
    val movieDao: MovieDao
) : BaseViewModel() {

    val movie = MutableLiveData<Movie>()
    val favoriteChanged = MutableLiveData<Boolean>().apply { value = false }

    fun checkFavorite(id: String) {
        viewModelScope.launch {
            try {
                val favoriteMovie = movieDao.getMovie(id)
                withContext(Dispatchers.Main) {
                    if (favoriteMovie?.isFavorite == true) {
                        val newMoview = movie.value
                        newMoview?.isFavorite = true
                        movie.value = newMoview
                    }
                }
            } catch (e: Exception) {
                onLoadFail(e)
            }
        }
    }

    fun favoriteMovie() {
        val newMovie = movie.value
        newMovie?.isFavorite = movie.value?.isFavorite != true
        movie.value = newMovie

        favoriteChanged.value = true

        newMovie?.let {
            viewModelScope.launch {
                try {
                    movieRepository.updateDB(it)
                } catch (e: Exception) {
                    onLoadFail(e)
                }
            }
        }
    }
}