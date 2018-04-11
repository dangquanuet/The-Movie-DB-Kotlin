package com.quanda.moviedb.data.source.local.datasource

import android.content.Context
import com.quanda.moviedb.data.source.local.AppDatabase
import com.quanda.moviedb.data.source.local.IUserLocal

class UserLocal(context: Context) : IUserLocal {

    val appDatabase: AppDatabase

    init {
        appDatabase = AppDatabase.getInstance(context)
    }

    companion object {
        @Volatile
        private var INSTANCE: UserLocal? = null

        fun getInstance(context: Context): UserLocal {
            if (INSTANCE == null) {
                INSTANCE = UserLocal(context)
            }
            return INSTANCE as UserLocal
        }
    }
}