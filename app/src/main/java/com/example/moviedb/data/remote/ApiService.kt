package com.example.moviedb.data.remote

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetCastAndCrewResponse
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @GET("3/discover/movie")
    suspend fun getMovieListAsync(@QueryMap hashMap: HashMap<String, String> = HashMap()): GetMovieListResponse

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetailAsync(@QueryMap hashMap: HashMap<String, String> = HashMap()): Movie

    @GET("3/movie/{movie_id}/credits")
    suspend fun getCastAndCrewAsync(@Path("movie_id") movieId: String): GetCastAndCrewResponse

    @GET("3/discover/tv")
    suspend fun getTvListAsync(@QueryMap hashMap: HashMap<String, String> = HashMap()): GetTvListResponse
}

object ApiParams {
    const val PAGE = "page"
    const val SORT_BY = "sort_by"
    const val POPULARITY_DESC = "popularity.desc"
    const val VOTE_AVERAGE_DESC = "vote_average.desc"
}