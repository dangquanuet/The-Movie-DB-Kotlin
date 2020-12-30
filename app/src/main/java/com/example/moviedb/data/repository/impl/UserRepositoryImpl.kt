package com.example.moviedb.data.repository.impl

import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.remote.response.GetCastAndCrewResponse
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.data.repository.safeApiCall
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) : UserRepository {

    override suspend fun getMovieList(
        hashMap: HashMap<String, String>
    ): GetMovieListResponse {
        return apiService.getDiscoverMovie(hashMap)
    }

    override suspend fun getCastAndCrew(movieId: String): Result<GetCastAndCrewResponse> {
        return safeApiCall { apiService.getMovieCredits(movieId) }
    }

    override suspend fun getTvList3(
        hashMap: HashMap<String, String>
    ): GetTvListResponse {
        return apiService.getDiscoverTv(hashMap)
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

    override suspend fun getMovieListLocal(): List<Movie>? {
        return movieDao.getMovieList()
    }

    override suspend fun getMovieLocal(id: String): Movie? {
        return movieDao.getMovie(id)
    }

    override suspend fun insertLocal(movie: Movie) {
        return movieDao.insert(movie)
    }

    override suspend fun insertLocal(list: List<Movie>) {
        return movieDao.insert(list)
    }

    override suspend fun updateLocal(movie: Movie) {
        return movieDao.update(movie)
    }

    override suspend fun deleteMovieLocal(movie: Movie) {
        return movieDao.deleteMovie(movie)
    }

    override suspend fun deleteMovieLocal(id: String) {
        return movieDao.deleteMovie(id)
    }

    override suspend fun deleteAllLocal() {
        return movieDao.deleteAll()
    }

    override suspend fun getMoviePageLocal(pageSize: Int, pageIndex: Int): List<Movie>? {
        return movieDao.getMoviePage(pageSize, pageIndex)
    }

    override suspend fun getFavoriteLocal(pageSize: Int, pageIndex: Int): List<Movie>? {
        return movieDao.getFavorite(pageSize = pageSize, pageIndex = pageIndex)
    }
}
