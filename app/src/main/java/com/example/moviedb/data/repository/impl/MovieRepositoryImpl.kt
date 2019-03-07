package com.example.moviedb.data.repository.impl

import com.example.moviedb.data.scheduler.SchedulerProvider
import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import com.example.moviedb.data.remote.response.Result
import com.example.moviedb.data.repository.MovieRepository
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MovieRepositoryImpl constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val schedulerProvider: SchedulerProvider
) : MovieRepository {

    override fun getMovieList(
        hashMap: HashMap<String, String>
    ): Single<GetMovieListResponse> {
        return apiService.getMovieList(hashMap)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
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
        return GlobalScope.async {
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
        GlobalScope.async {
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
        return GlobalScope.async {
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
        return GlobalScope.async {
            try {
                movieDao.update(movie)
            } catch (e: Throwable) {
                fail(e)
            }
        }
    }
}