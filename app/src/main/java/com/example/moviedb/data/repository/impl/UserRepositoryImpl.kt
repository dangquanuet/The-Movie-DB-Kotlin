package com.example.moviedb.data.repository.impl

import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.remote.response.GetCastAndCrewResponse
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import com.example.moviedb.data.repository.UserRepository

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) : UserRepository {

    override suspend fun getMovieList(
        hashMap: HashMap<String, String>
    ): GetMovieListResponse {
        return apiService.getMovieListAsync(hashMap)
    }

    override suspend fun getCastAndCrew(movieId: String): GetCastAndCrewResponse {
        return apiService.getCastAndCrewAsync(movieId)
    }

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