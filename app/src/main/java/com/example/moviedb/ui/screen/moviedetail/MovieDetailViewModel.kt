package com.example.moviedb.ui.screen.moviedetail

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.data.scheduler.SchedulerProvider
import com.example.moviedb.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    val movieRepository: MovieRepository,
    val movieDao: MovieDao,
    val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val movie = MutableLiveData<Movie>()
    val favoriteChanged = MutableLiveData<Boolean>().apply { value = false }

    fun checkFavorite(id: String) {
        addDisposable(
            movieDao.getMovie(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    if (it?.isFavorite == true) {
                        val newMoview = movie.value
                        newMoview?.isFavorite = true
                        movie.value = newMoview
                    }
                }, {
                    onLoadFail(it)
                })
        )
    }

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
                    onLoadFailUI(e)
                }
            }
        }
    }
}