package com.example.moviedb.data.remote.api

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetCastAndCrewResponse
import com.example.moviedb.data.remote.response.GetMovieImages
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {
    @GET(ApiPath.DISCOVER_MOVIE)
    suspend fun getDiscoverMovie(@QueryMap hashMap: HashMap<String, String> = HashMap()): GetMovieListResponse

    @GET("3/movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") movieId: String): Movie

    @GET("3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") movieId: String): GetCastAndCrewResponse

    @GET("3/movie/{movie_id}/images")
    suspend fun getMovieImages(@Path("movie_id") movieId: String): GetMovieImages

    @GET(ApiPath.DISCOVER_TV)
    suspend fun getDiscoverTv(@QueryMap hashMap: HashMap<String, String> = HashMap()): GetTvListResponse
}