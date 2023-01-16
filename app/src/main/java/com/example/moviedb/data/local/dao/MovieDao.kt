package com.example.moviedb.data.local.dao

import androidx.room.*
import com.example.moviedb.data.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getMovieList(): List<Movie>?

    @Query("SELECT * FROM movie WHERE movie.id = :id")
    suspend fun getMovie(id: String): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun deleteMovie(id: String)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()

    /*
    @Query("SELECT * FROM playlist " +
    "WHERE playlist_title LIKE '% :playlistTitle %' " +
    "GROUP BY playlist_title " +
    "ORDER BY playlist_title " +
    "LIMIT :limit")
    List<IPlaylist> searchPlaylists(String playlistTitle, int limit);
     */
    @Query("SELECT * FROM movie LIMIT :pageSize OFFSET :pageIndex")
    suspend fun getMoviePage(pageSize: Int, pageIndex: Int): List<Movie>?

    @Query("SELECT * FROM movie WHERE movie.isFavorite = 1 LIMIT :pageSize OFFSET ((:pageIndex-1)*:pageSize) ")
    suspend fun getFavorite(pageSize: Int, pageIndex: Int): List<Movie>?
}