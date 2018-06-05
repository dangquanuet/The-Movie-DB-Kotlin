package com.quanda.moviedb.ui.screen.main.moviedetail

import android.arch.lifecycle.MutableLiveData
import com.quanda.moviedb.App
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.repository.impl.MovieRepository
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailViewModel : BaseDataLoadViewModel() {

    @Inject
    lateinit var movieRepository: MovieRepository
    @Inject
    lateinit var movieDao: MovieDao

    lateinit var navigator: MovieDetailNavigator

    val movie = MutableLiveData<Movie>()
    val favoriteChanged = MutableLiveData<Boolean>().apply { value = false }

    init {
        App.appComponent.inject(this)
    }

    fun updateNewMovie(newMovie: Movie) {
        movieDao.getMovie(newMovie.id ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableMaybeObserver<Movie>() {
                    override fun onSuccess(t: Movie) {
                        newMovie.isFavorite = t.isFavorite
                        movie.value = newMovie
                    }

                    override fun onComplete() {
                        newMovie.isFavorite = false
                        movie.value = newMovie
                    }

                    override fun onError(e: Throwable) {
                        newMovie.isFavorite = false
                        movie.value = newMovie
                    }
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