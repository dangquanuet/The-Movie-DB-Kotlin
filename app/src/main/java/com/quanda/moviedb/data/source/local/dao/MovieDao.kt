package com.quanda.moviedb.data.source.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.quanda.moviedb.data.model.Movie
import io.reactivex.Single

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getMovieList(): Single<List<Movie>>

    @Insert
    fun insert(movie: Movie)

    @Insert
    fun insertMovieList(list: List<Movie>)
}