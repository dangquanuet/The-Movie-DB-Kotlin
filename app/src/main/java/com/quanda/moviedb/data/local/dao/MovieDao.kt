package com.quanda.moviedb.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.quanda.moviedb.data.model.Movie
import io.reactivex.Maybe

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getMovieList(): Maybe<List<Movie>>

    @Query("SELECT * FROM movie WHERE movie.id = :id")
    fun getMovie(id: String): Maybe<Movie>

    @Insert(onConflict = IGNORE)
    fun insert(movie: Movie)

    @Insert(onConflict = IGNORE)
    fun insert(list: List<Movie>)

    @Insert(onConflict = REPLACE)
    fun update(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("DELETE FROM movie WHERE id = :id")
    fun deleteMovie(id: String)

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
    fun getMoviePage(pageSize: Int, pageIndex: Int): Maybe<List<Movie>>

    @Query("SELECT * FROM movie WHERE movie.isFavorite = 1 LIMIT :pageSize OFFSET ((:pageIndex-1)*:pageSize) ")
    fun getFavorite(pageSize: Int, pageIndex: Int): Maybe<List<Movie>>
}