package com.quanda.moviedb.ui.screen.main.moviedetail

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.quanda.moviedb.MainApplication
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.repository.impl.UserRepository
import com.quanda.moviedb.data.local.dao.MovieDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class MovieDetailViewModel(application: Application,
        val movieDetailNavigator: MovieDetailNavigator) : BaseDataLoadViewModel(application) {

    class CustomFactory(val application: Application,
            val movieDetailNavigator: MovieDetailNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieDetailViewModel(application, movieDetailNavigator) as T
        }
    }

    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var movieDao: MovieDao

    val movie = MutableLiveData<Movie>()
    val favoriteChanged = MutableLiveData<Boolean>().apply { value = false }

    init {
        MainApplication.appComponent.inject(this)
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
            launch {
                async { movieDao.update(newMovie) }
            }
        }
    }
}