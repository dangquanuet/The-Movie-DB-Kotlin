package com.quanda.moviedb.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie
import javax.inject.Singleton

@Singleton
@Database(entities = arrayOf(Movie::class), version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    init {
        if (INSTANCE != null) {
            throw UnsupportedOperationException("use getInstance(0")
        }
    }

    companion object {
        private val DB_NAME = "movie-database.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = create(context)
                    }
                }
            }
            return INSTANCE as AppDatabase
        }

        private fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java,
                    DB_NAME).build()
        }
    }

    abstract fun movieDao(): MovieDao
}