package com.quanda.moviedb.data.source.remote.datasource

import com.quanda.moviedb.data.source.remote.IUserRemote

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

}