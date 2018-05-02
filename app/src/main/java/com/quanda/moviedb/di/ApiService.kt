package com.quanda.moviedb.di

import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("3/discover/movie")
    fun getMovieList(@QueryMap hashMap: HashMap<String, String> = HashMap()): Single<GetMovieListResponse>

    @GET("3/movie/{movie_id}")
    fun getMovieDetail(@QueryMap hashMap: HashMap<String, String> = HashMap()): Single<Movie>
}