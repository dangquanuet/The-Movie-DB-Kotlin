package com.quanda.moviedb.data.source.remote.datasource

import com.quanda.moviedb.data.source.remote.IUserRemote
import com.quanda.moviedb.data.source.remote.RequestCreator
import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import io.reactivex.Single

class UserRemote : IUserRemote {

    companion object {
        private var INSTANCE: UserRemote? = null

        fun getInstance(): UserRemote {
            if (INSTANCE == null) {
                INSTANCE = UserRemote()
            }
            return INSTANCE as UserRemote
        }
    }

    override fun getMovieList(): Single<GetMovieListResponse> {
        return RequestCreator.getRequest().getMovieList()
    }
}