package com.quanda.moviedb.data.source.remote

import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("3/discover/movie")
    fun getMovieList(): Single<GetMovieListResponse>

}