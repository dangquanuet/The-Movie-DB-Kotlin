package com.example.moviedb.ui.screen.moviedetail

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel constructor(
    private val movieRepository: MovieRepository,
    private val movieDao: MovieDao
) : BaseViewModel() {

    val movie = MutableLiveData<Movie>()
    val favoriteChanged = MutableLiveData<Boolean>().apply { value = false }

    fun updateNewMovie(newMovie: Movie) {
        addDisposable(movieDao.getMovie(newMovie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newMovie.isFavorite = it.isFavorite
                movie.value = newMovie
            }, {
                newMovie.isFavorite = false
                movie.value = newMovie
            })
        )
    }

    fun favoriteMovie() {
        val newMovie = movie.value
        newMovie?.isFavorite = movie.value?.isFavorite != true
        movie.value = newMovie

        favoriteChanged.value = true

        newMovie?.apply {
            movieRepository.updateDB(newMovie)
        }
    }
}