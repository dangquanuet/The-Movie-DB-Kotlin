package com.quanda.moviedb.ui.screen.moviedetail

import androidx.lifecycle.MutableLiveData
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.repository.impl.MovieRepositoryImpl
import com.quanda.moviedb.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
        private val movieRepository: MovieRepositoryImpl,
        private val movieDao: MovieDao
) : BaseViewModel() {

    val movie = MutableLiveData<Movie>()
    val favoriteChanged = MutableLiveData<Boolean>().apply { value = false }

    fun updateNewMovie(newMovie: Movie) {
        movieDao.getMovie(newMovie.id ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    newMovie.isFavorite = it.isFavorite
                    movie.value = newMovie
                }, {
                    newMovie.isFavorite = false
                    movie.value = newMovie
                })
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