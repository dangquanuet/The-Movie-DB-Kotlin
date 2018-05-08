package com.quanda.moviedb.data.repository

import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import com.quanda.moviedb.data.remote.response.GetTvListResponse
import io.reactivex.Single
import kotlinx.coroutines.experimental.Deferred

interface IMovieRepository {

    fun getMovieList(hashMap: HashMap<String, String> = HashMap()): Single<GetMovieListResponse>

    fun getTvList(hashMap: HashMap<String, String> = HashMap()): Deferred<GetTvListResponse>

    fun insertDB(list: List<Movie>)

    fun updateDB(movie: Movie)
}