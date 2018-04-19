package com.quanda.moviedb.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.data.source.local.dao.MovieDao

@Database(entities = arrayOf(Movie::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private val DB_NAME = "movie-database.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = create(context)
            }
            return INSTANCE as AppDatabase
        }

        private fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
        }
    }

    abstract fun movieDao(): MovieDao
}