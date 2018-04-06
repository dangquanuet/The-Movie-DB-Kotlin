package com.quanda.moviedb.data.source

import com.quanda.moviedb.data.source.local.IUserLocal
import com.quanda.moviedb.data.source.local.datasource.UserLocal
import com.quanda.moviedb.data.source.remote.IUserRemote
import com.quanda.moviedb.data.source.remote.datasource.UserRemote
import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import com.quanda.moviedb.utils.SchedulerUtils
import io.reactivex.Single

class UserRepository() : IUserRemote, IUserLocal {

    companion object {
        private var INSTANCE: UserRepository? = null

        fun getInstance(): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository()
            }
            return INSTANCE as UserRepository
        }
    }

    private val userLocal: IUserLocal = UserLocal.getInstance()
    private val userRemote: IUserRemote = UserRemote.getInstance()

    override fun getMovieList(hashMap: HashMap<String, String>): Single<GetMovieListResponse> {
        return userRemote.getMovieList(hashMap).compose(SchedulerUtils.applySingleAsyncSchedulers())
    }
}