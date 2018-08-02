package com.quanda.moviedb.data.remote

import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import com.quanda.moviedb.data.remote.response.GetTvListResponse
import io.reactivex.Single
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("3/discover/movie")
    fun getMovieList(@QueryMap hashMap: HashMap<String, String> = HashMap()): Single<GetMovieListResponse>

    @GET("3/movie/{movie_id}")
    fun getMovieDetail(@QueryMap hashMap: HashMap<String, String> = HashMap()): Single<Movie>

    @GET("3/discover/tv")
    fun getTvList(@QueryMap hashMap: HashMap<String, String> = HashMap()): Deferred<GetTvListResponse>
}

object ApiParams {
    const val PAGE = "page"
    const val SORT_BY = "sort_by"
    const val POPULARITY_DESC = "popularity.desc"
    const val VOTE_AVERAGE_DESC = "vote_average.desc"
}