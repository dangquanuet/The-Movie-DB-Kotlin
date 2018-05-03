package com.quanda.moviedb.data.repository

import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import io.reactivex.Single

interface IMovieRepository {

    fun getMovieList(hashMap: HashMap<String, String> = HashMap()): Single<GetMovieListResponse>

    fun insertDB(list: List<Movie>)

    fun updateDB(movie: Movie)
}