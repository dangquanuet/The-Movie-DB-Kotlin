package com.quanda.moviedb.data.source

import com.quanda.moviedb.data.source.local.IUserLocal
import com.quanda.moviedb.data.source.local.datasource.UserLocal
import com.quanda.moviedb.data.source.remote.IUserRemote
import com.quanda.moviedb.data.source.remote.datasource.UserRemote
import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(val userLocal: UserLocal,
        val userRemote: UserRemote) : IUserRemote, IUserLocal {

    override fun getMovieList(hashMap: HashMap<String, String>): Single<GetMovieListResponse> {
        return userRemote.getMovieList(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(SchedulerUtils.applySingleAsyncSchedulers())
    }
}