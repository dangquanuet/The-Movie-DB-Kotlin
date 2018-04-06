package com.quanda.moviedb.data.source.remote

import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("3/discover/movie")
    fun getMovieList(@QueryMap hashMap: HashMap<String, String> = HashMap()): Single<GetMovieListResponse>
}