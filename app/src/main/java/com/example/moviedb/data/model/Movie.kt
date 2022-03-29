package com.example.moviedb.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviedb.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "id") val id: String,
    @Json(name = "adult") val adult: Boolean? = false,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "budget") val budget: Int? = null,
    @Json(name = "homepage") val homepage: String? = null,
    @Json(name = "imdb_id") val imdbId: String? = null,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "original_title") val originalTitle: String? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "release_date") val releaseDate: String? = null,
    @Json(name = "revenue") val revenue: Int? = null,
    @Json(name = "runtime") val runtime: Int? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "tagline") val tagline: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "video") val video: Boolean? = false,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "vote_count") val voteCount: Int? = null,
    var isFavorite: Boolean? = false
) : Parcelable {

    fun getFullBackdropPath() =
        if (backdropPath.isNullOrBlank()) null else BuildConfig.SMALL_IMAGE_URL + backdropPath

    fun getFullPosterPath() =
        if (posterPath.isNullOrBlank()) null else BuildConfig.SMALL_IMAGE_URL + posterPath
}