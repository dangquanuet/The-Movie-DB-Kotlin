package com.example.moviedb.data.repository.impl

import com.example.moviedb.data.local.dao.MovieDao
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.api.ApiService
import com.example.moviedb.data.remote.response.GetCastAndCrewResponse
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import com.example.moviedb.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    @Named("io") private val io: CoroutineDispatcher
) : UserRepository {

    override suspend fun getMovieList(
        hashMap: HashMap<String, String>
    ): GetMovieListResponse = withContext(io) {
        apiService.getDiscoverMovie(hashMap)
    }

    override suspend fun getMovieById(movieId: String): Movie =
        withContext(io) {
            apiService.getMovie(movieId = movieId)
        }

    override suspend fun getCastAndCrew(movieId: String): GetCastAndCrewResponse =
        withContext(io) {
            apiService.getMovieCredits(movieId)
        }

    override suspend fun getTvList3(
        hashMap: HashMap<String, String>
    ): GetTvListResponse = withContext(io) {
        apiService.getDiscoverTv(hashMap)
    }

    override suspend fun insertDB(
        list: List<Movie>
    ) = withContext(io) {
        movieDao.insert(list)
    }

    override suspend fun updateDB(
        movie: Movie
    ) = withContext(io) {
        movieDao.update(movie)
    }

    override suspend fun getMovieListLocal(): List<Movie>? = withContext(io) {
        movieDao.getMovieList()
    }

    override suspend fun getMovieLocal(id: String): Movie? = withContext(io) {
        movieDao.getMovie(id)
    }

    override suspend fun insertLocal(movie: Movie) = withContext(io) {
        movieDao.insert(movie)
    }

    override suspend fun insertLocal(list: List<Movie>) = withContext(io) {
        movieDao.insert(list)
    }

    override suspend fun updateLocal(movie: Movie) = withContext(io) {
        movieDao.update(movie)
    }

    override suspend fun deleteMovieLocal(movie: Movie) = withContext(io) {
        movieDao.deleteMovie(movie)
    }

    override suspend fun deleteMovieLocal(id: String) = withContext(io) {
        movieDao.deleteMovie(id)
    }

    override suspend fun deleteAllLocal() = withContext(io) {
        movieDao.deleteAll()
    }

    override suspend fun getMoviePageLocal(pageSize: Int, pageIndex: Int): List<Movie>? =
        withContext(io) {
            movieDao.getMoviePage(pageSize, pageIndex)
        }

    override suspend fun getFavoriteLocal(pageSize: Int, pageIndex: Int): List<Movie>? =
        withContext(io) {
            movieDao.getFavorite(pageSize = pageSize, pageIndex = pageIndex)
        }
}
