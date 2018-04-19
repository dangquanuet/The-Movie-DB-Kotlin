package com.quanda.moviedb.data.source.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.quanda.moviedb.data.model.Movie
import io.reactivex.Single

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getMovieList(): Single<List<Movie>>

    @Insert(onConflict = REPLACE)
    fun insert(movie: Movie)

    @Insert(onConflict = REPLACE)
    fun insert(list: List<Movie>)

    @Delete
    fun deleteMove(movie: Movie)

    @Query("DELETE FROM movie")
    fun deleteAll()

    /*
    @Query("SELECT * FROM playlist " +
    "WHERE playlist_title LIKE '% :playlistTitle %' " +
    "GROUP BY playlist_title " +
    "ORDER BY playlist_title " +
    "LIMIT :limit")
    List<IPlaylist> searchPlaylists(String playlistTitle, int limit);
     */
    @Query("SELECT * FROM movie LIMIT :pageSize OFFSET :pageIndex")
    fun getMoviePage(pageSize: Int, pageIndex: Int): Single<List<Movie>>
}