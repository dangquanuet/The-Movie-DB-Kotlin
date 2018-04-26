package com.quanda.moviedb.data.source

import android.content.Context
import com.quanda.moviedb.data.source.local.IUserLocal
import com.quanda.moviedb.data.source.local.datasource.UserLocal
import com.quanda.moviedb.data.source.remote.IUserRemote
import com.quanda.moviedb.data.source.remote.datasource.UserRemote
import com.quanda.moviedb.data.source.remote.response.GetMovieListResponse
import com.quanda.moviedb.utils.SchedulerUtils
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class UserRepository(context: Context) : IUserRemote, IUserLocal {

    companion object {
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }
            return INSTANCE as UserRepository
        }
    }

    private val userLocal by lazy { UserLocal.getInstance(context) }
    private val userRemote by lazy { UserRemote.getInstance() }

    override fun getMovieList(hashMap: HashMap<String, String>): Single<GetMovieListResponse> {
        return userRemote.getMovieList(hashMap).compose(SchedulerUtils.applySingleAsyncSchedulers())
    }
}