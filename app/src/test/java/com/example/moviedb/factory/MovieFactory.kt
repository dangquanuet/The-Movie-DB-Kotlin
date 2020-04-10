package com.example.moviedb.factory

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetMovieListResponse

fun createMovieListResponse(): GetMovieListResponse {
    val response = GetMovieListResponse()
    val movie1 = Movie(id = "1")
    val movie2 = Movie(id = "2")
    val movie3 = Movie(id = "3")
    val movie4 = Movie(id = "4")
    response.results = arrayListOf(movie1, movie2, movie3, movie4)

    return response
}