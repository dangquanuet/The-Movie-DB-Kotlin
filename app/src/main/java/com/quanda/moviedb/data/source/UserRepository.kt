package com.quanda.moviedb.data.source

import com.quanda.moviedb.data.source.local.IUserLocal
import com.quanda.moviedb.data.source.local.datasource.UserLocal
import com.quanda.moviedb.data.source.remote.IUserRemote
import com.quanda.moviedb.data.source.remote.datasource.UserRemote

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

    private val userLocal = UserLocal.getInstance()
    private val userRemote = UserRemote.getInstance()
}