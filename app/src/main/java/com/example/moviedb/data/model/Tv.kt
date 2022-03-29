package com.example.moviedb.data.model

import android.os.Parcelable
import com.example.moviedb.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Tv(
    @Json(name = "id") val id: String,
    @Json(name = "original_name") val originalName: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "popularity") val popularity: Double? = null,
    @Json(name = "vote_count") val voteCount: Int? = null,
    @Json(name = "first_air_date") val firstAirDate: String? = null,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "overview") val overview: String? = null,
    @Json(name = "poster_path") val posterPath: String? = null
) : Parcelable {
    fun getFullPosterPath() =
        if (posterPath.isNullOrBlank()) null else BuildConfig.SMALL_IMAGE_URL + posterPath
}