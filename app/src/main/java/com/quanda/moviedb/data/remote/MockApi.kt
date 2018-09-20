package com.quanda.moviedb.data.remote

import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.remote.response.GetMovieListResponse
import com.quanda.moviedb.data.remote.response.GetTvListResponse
import io.reactivex.Single
import kotlinx.coroutines.experimental.Deferred

class MockApi : ApiService {

    override fun getMovieList(hashMap: HashMap<String, String>): Single<GetMovieListResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMovieDetail(hashMap: HashMap<String, String>): Single<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTvList(hashMap: HashMap<String, String>): Deferred<GetTvListResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}