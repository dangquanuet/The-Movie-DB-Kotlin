package com.quanda.moviedb.data.repository

import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import com.quanda.moviedb.data.remote.response.GetTvListResponse
import com.quanda.moviedb.data.remote.response.Result
import io.reactivex.Single
import kotlinx.coroutines.experimental.Deferred

interface MovieRepository {

    fun getMovieList(hashMap: HashMap<String, String> = HashMap()): Single<GetMovieListResponse>

    fun getTvList(hashMap: HashMap<String, String> = HashMap()): Deferred<GetTvListResponse>

    fun getTvList(hashMap: HashMap<String, String>, success: (GetTvListResponse) -> Unit,
                  fail: (Throwable) -> Unit)

    fun getTvList2(hashMap: HashMap<String, String> = HashMap()): Result<GetTvListResponse>

    fun insertDB(list: List<Movie>)

    fun updateDB(movie: Movie)
}