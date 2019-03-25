package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse

interface MovieRepository {

    suspend fun getMovieList(
        hashMap: HashMap<String, String> = HashMap()
    ): GetMovieListResponse

    /*fun getTvList(
        hashMap: HashMap<String, String> = HashMap()
    ): Deferred<GetTvListResponse>

    fun getTvList(
        hashMap: HashMap<String, String>,
        success: (GetTvListResponse) -> Unit = {},
        fail: (Throwable) -> Unit = {}
    ): Deferred<Unit?>

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