package com.quanda.moviedb.data.repository.impl

import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.ApiService
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import com.quanda.moviedb.data.remote.response.GetTvListResponse
import com.quanda.moviedb.data.remote.response.Result
import com.quanda.moviedb.data.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class MovieRepositoryImpl constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getMovieList(
        hashMap: HashMap<String, String>
    ): Single<GetMovieListResponse> {
        return apiService.getMovieList(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTvList(
        hashMap: HashMap<String, String>
    ): Deferred<GetTvListResponse> {
        return apiService.getTvList(hashMap)
    }

    override fun getTvList(
        hashMap: HashMap<String, String>,
        success: (GetTvListResponse) -> Unit,
        fail: (Throwable) -> Unit
    ): Deferred<Unit?> {
        return async(UI) {
            try {
                success(apiService.getTvList(hashMap).await())
            } catch (e: Throwable) {
                fail(e)
            }
        }
    }

    override fun getTvList2(
        hashMap: HashMap<String, String>
    ): Result<GetTvListResponse> {
        lateinit var result: Result<GetTvListResponse>
        async(UI) {
            try {
                result = Result.Success(apiService.getTvList(hashMap).await())
            } catch (e: Throwable) {
                result = Result.Error(e)
            }
        }
        return result
    }

    override fun insertDB(
        list: List<Movie>,
        fail: (Throwable) -> Unit
    ): Deferred<Unit> {
        return async {
            try {
                movieDao.insert(list)
            } catch (e: Throwable) {
                fail(e)
            }
        }
    }

    override fun updateDB(
        movie: Movie,
        fail: (Throwable) -> Unit
    ): Deferred<Unit> {
        return async {
            try {
                movieDao.update(movie)
            } catch (e: Throwable) {
                fail(e)
            }
        }
    }
}