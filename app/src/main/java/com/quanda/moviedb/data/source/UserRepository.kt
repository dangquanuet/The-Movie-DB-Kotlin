package com.quanda.moviedb.data.source

import com.quanda.moviedb.data.source.local.IUserLocal
import com.quanda.moviedb.data.source.local.datasource.UserLocal
import com.quanda.moviedb.data.source.remote.IUserRemote
import com.quanda.moviedb.data.source.remote.datasource.UserRemote
import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import com.quanda.moviedb.utils.SchedulerUtils
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(val userLocal: UserLocal,
        val userRemote: UserRemote) : IUserRemote, IUserLocal {

    override fun getMovieList(hashMap: HashMap<String, String>): Single<GetMovieListResponse> {
        return userRemote.getMovieList(hashMap).compose(SchedulerUtils.applySingleAsyncSchedulers())
    }
}