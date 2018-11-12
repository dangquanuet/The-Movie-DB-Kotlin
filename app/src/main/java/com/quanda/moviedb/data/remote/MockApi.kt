package com.quanda.moviedb.data.remote

import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import com.quanda.moviedb.data.remote.response.GetTvListResponse
import io.reactivex.Single
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

class MockApi : ApiService {

    override fun getMovieList(hashMap: HashMap<String, String>): Single<GetMovieListResponse> {
        return Single.just(GetMovieListResponse())
    }

    override fun getMovieDetail(hashMap: HashMap<String, String>): Single<Movie> {
        return Single.just(Movie("1"))
    }

    override fun getTvList(hashMap: HashMap<String, String>): Deferred<GetTvListResponse> {
        return async { GetTvListResponse() }
    }

}