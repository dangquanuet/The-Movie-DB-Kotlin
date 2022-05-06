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
    @Named("io") private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun getMovieList(
        hashMap: HashMap<String, String>
    ): GetMovieListResponse = withContext(ioDispatcher) {
        apiService.getDiscoverMovie(hashMap)
    }

    override suspend fun getCastAndCrew(movieId: String): GetCastAndCrewResponse =
        withContext(ioDispatcher) {
            apiService.getMovieCredits(movieId)
        }

    override suspend fun getTvList3(
        hashMap: HashMap<String, String>
    ): GetTvListResponse = withContext(ioDispatcher) {
        apiService.getDiscoverTv(hashMap)
    }

    override suspend fun insertDB(
        list: List<Movie>
    ) = withContext(ioDispatcher) {
        movieDao.insert(list)
    }

    override suspend fun updateDB(
        movie: Movie
    ) = withContext(ioDispatcher) {
        movieDao.update(movie)
    }

    override suspend fun getMovieListLocal(): List<Movie>? = withContext(ioDispatcher) {
        movieDao.getMovieList()
    }

    override suspend fun getMovieLocal(id: String): Movie? = withContext(ioDispatcher) {
        movieDao.getMovie(id)
    }

    override suspend fun insertLocal(movie: Movie) = withContext(ioDispatcher) {
        movieDao.insert(movie)
    }

    override suspend fun insertLocal(list: List<Movie>) = withContext(ioDispatcher) {
        movieDao.insert(list)
    }

    override suspend fun updateLocal(movie: Movie) = withContext(ioDispatcher) {
        movieDao.update(movie)
    }

    override suspend fun deleteMovieLocal(movie: Movie) = withContext(ioDispatcher) {
        movieDao.deleteMovie(movie)
    }

    override suspend fun deleteMovieLocal(id: String) = withContext(ioDispatcher) {
        movieDao.deleteMovie(id)
    }

    override suspend fun deleteAllLocal() = withContext(ioDispatcher) {
        movieDao.deleteAll()
    }

    override suspend fun getMoviePageLocal(pageSize: Int, pageIndex: Int): List<Movie>? =
        withContext(ioDispatcher) {
            movieDao.getMoviePage(pageSize, pageIndex)
        }

    override suspend fun getFavoriteLocal(pageSize: Int, pageIndex: Int): List<Movie>? =
        withContext(ioDispatcher) {
            movieDao.getFavorite(pageSize = pageSize, pageIndex = pageIndex)
        }
}
