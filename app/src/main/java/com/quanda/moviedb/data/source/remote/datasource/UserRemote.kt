package com.quanda.moviedb.data.source.remote.datasource

import com.quanda.moviedb.data.source.remote.IUserRemote
import com.quanda.moviedb.data.source.remote.RequestCreator
import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemote @Inject constructor() : IUserRemote {

    override fun getMovieList(hashMap: HashMap<String, String>): Single<GetMovieListResponse> {
        return RequestCreator.getRequest().getMovieList(hashMap)
    }
}