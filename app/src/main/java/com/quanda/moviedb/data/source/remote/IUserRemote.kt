package com.quanda.moviedb.data.source.remote

import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import io.reactivex.Single

interface IUserRemote {
    fun getMovieList(): Single<GetMovieListResponse>
}