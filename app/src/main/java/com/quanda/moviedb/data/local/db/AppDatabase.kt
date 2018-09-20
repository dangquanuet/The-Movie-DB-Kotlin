package com.quanda.moviedb.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.quanda.moviedb.data.local.dao.MovieDao
import com.quanda.moviedb.data.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}