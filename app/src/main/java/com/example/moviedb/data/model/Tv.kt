package com.example.moviedb.data.model

import android.os.Parcelable
import com.example.moviedb.BuildConfig
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Tv(
    val id: String,
    val original_name: String? = null,
    val name: String? = null,
    val popularity: Double? = null,
    val vote_count: Int? = null,
    val first_air_date: String? = null,
    val backdrop_path: String? = null,
    val original_language: String? = null,
    val vote_average: Double? = null,
    val overview: String? = null,
    val poster_path: String? = null
) : Parcelable {
    fun getFullPosterPath() =
        if (poster_path.isNullOrBlank()) null else BuildConfig.SMALL_IMAGE_URL + poster_path
}