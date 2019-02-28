package com.example.moviedb.data.repository

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import com.example.moviedb.data.remote.response.Result
import io.reactivex.Single
import kotlinx.coroutines.Deferred

interface MovieRepository {

    fun getMovieList(
        hashMap: HashMap<String, String> = HashMap()
    ): Single<GetMovieListResponse>

    fun getTvList(
        hashMap: HashMap<String, String> = HashMap()
    ): Deferred<GetTvListResponse>

    fun getTvList(
        hashMap: HashMap<String, String>,
        success: (GetTvListResponse) -> Unit = {},
        fail: (Throwable) -> Unit = {}
    ): Deferred<Unit?>

    fun getTvList2(
        hashMap: HashMap<String, String> = HashMap()
    ): Result<GetTvListResponse>

    fun insertDB(
        list: List<Movie>,
        fail: (Throwable) -> Unit = {}
    ): Deferred<Unit?>

    fun updateDB(
        movie: Movie,
        fail: (Throwable) -> Unit = {}
    ): Deferred<Unit?>

}