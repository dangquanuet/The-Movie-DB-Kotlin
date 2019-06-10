package com.example.moviedb.data.repository.impl

import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import com.example.moviedb.data.repository.MovieRepository
import com.example.moviedb.data.scheduler.SchedulerProvider

class MovieRepositoryImpl constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val schedulerProvider: SchedulerProvider
) : MovieRepository {

    override suspend fun getMovieList(
        hashMap: HashMap<String, String>
    ): GetMovieListResponse {
        return apiService.getMovieListAsync(hashMap)
    }

    /*override fun getTvList(
        hashMap: HashMap<String, String>,
        success: suspend (GetTvListResponse) -> Unit,
        fail: suspend (Throwable) -> Unit
    ) {
        schedulerProvider.ioScope.launch {
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
        schedulerProvider.ioScope.launch {
            try {
                result = Result.Success(apiService.getTvList(hashMap).await())
            } catch (e: Throwable) {
                result = Result.Error(e)
            }
        }
        return result
    }*/

    override suspend fun getTvList3(
        hashMap: HashMap<String, String>
    ): GetTvListResponse {
        return apiService.getTvListAsync(hashMap)
    }

    override suspend fun insertDB(
        list: List<Movie>
    ) {
        movieDao.insert(list)
    }

    override suspend fun updateDB(
        movie: Movie
    ) {
        movieDao.update(movie)
    }
}