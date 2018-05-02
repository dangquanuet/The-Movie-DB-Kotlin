package com.quanda.moviedb.data.repository

import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import io.reactivex.Single

interface IMovieRepository {

    fun getMovieList(hashMap: HashMap<String, String> = HashMap()): Single<GetMovieListResponse>

}