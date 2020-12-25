package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetCastAndCrewResponse
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse

interface UserRepository {

    suspend fun getMovieList(
        hashMap: HashMap<String, String> = HashMap()
    ): GetMovieListResponse

    suspend fun getCastAndCrew(
        movieId: String
    ): Result<GetCastAndCrewResponse>

    suspend fun getTvList3(
        hashMap: HashMap<String, String>
    ): GetTvListResponse

    suspend fun insertDB(
        list: List<Movie>
    )

    suspend fun updateDB(
        movie: Movie
    )

    /**
     * local movie db functions
     */
    suspend fun getMovieListLocal(): List<Movie>?

    suspend fun getMovieLocal(id: String): Movie?

    suspend fun insertLocal(movie: Movie)

    suspend fun insertLocal(list: List<Movie>)

    suspend fun updateLocal(movie: Movie)

    suspend fun deleteMovieLocal(movie: Movie)

    suspend fun deleteMovieLocal(id: String)

    suspend fun deleteAllLocal()

    suspend fun getMoviePageLocal(pageSize: Int, pageIndex: Int): List<Movie>?

    suspend fun getFavoriteLocal(pageSize: Int, pageIndex: Int): List<Movie>?
}
