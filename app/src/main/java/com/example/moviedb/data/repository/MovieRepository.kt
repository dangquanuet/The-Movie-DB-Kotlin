package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse

interface MovieRepository {

    suspend fun getMovieList(
        hashMap: HashMap<String, String> = HashMap()
    ): GetMovieListResponse

    /**
     * a repository doesn’t have a natural lifecycle — it’s just an object — 
     * it would have no way to cleanup work.
     * As a result, any coroutines started in the repository will leak by default.
     */
    /*fun getTvList(
        hashMap: HashMap<String, String>,
        success: suspend (GetTvListResponse) -> Unit = {},
        fail: suspend (Throwable) -> Unit = {}
    )

    fun getTvList2(
        hashMap: HashMap<String, String> = HashMap()
    ): Result<GetTvListResponse>*/

    suspend fun getTvList3(
        hashMap: HashMap<String, String>
    ): GetTvListResponse

    suspend fun insertDB(
        list: List<Movie>
    )

    suspend fun updateDB(
        movie: Movie
    )

}