package com.quanda.moviedb.data.repository.impl

import android.util.Log
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.ApiService
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import com.quanda.moviedb.data.repository.IMovieRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(val apiService: ApiService,
        val movieDao: MovieDao) : IMovieRepository {

    override fun getMovieList(hashMap: HashMap<String, String>): Single<GetMovieListResponse> {
        return apiService.getMovieList(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertDB(list: List<Movie>) {
        launch {
            try {
                movieDao.insert(list)
            } catch (e: Exception) {
                Log.e("MovieRepository", e.toString())
            }
        }
    }

    override fun updateDB(movie: Movie) {
        launch {
            try {
                movieDao.update(movie)
            } catch (e: Exception) {
                Log.e("MovieRepository", e.toString())
            }
        }
    }
}