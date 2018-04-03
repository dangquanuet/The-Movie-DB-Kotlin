package com.quanda.moviedb.data.source.local.datasource

import com.quanda.moviedb.data.source.local.IUserLocal

class UserLocal() : IUserLocal {
    companion object {
        private var INSTANCE: UserLocal? = null

        fun getInstance(): UserLocal {
            if (INSTANCE == null) {
                INSTANCE = UserLocal()
            }
            return INSTANCE as UserLocal
        }
    }
}