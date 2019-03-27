package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import com.example.moviedb.data.remote.response.Result

interface MovieRepository {

    suspend fun getMovieList(
        hashMap: HashMap<String, String> = HashMap()
    ): GetMovieListResponse

    fun getTvList(
        hashMap: HashMap<String, String>,
        success: suspend (GetTvListResponse) -> Unit = {},
        fail: suspend (Throwable) -> Unit = {}
    )

    fun getTvList2(
        hashMap: HashMap<String, String> = HashMap()
    ): Result<GetTvListResponse>

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