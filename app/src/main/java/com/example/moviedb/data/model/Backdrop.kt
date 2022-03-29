package com.example.moviedb.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Backdrop(
    @Json(name = "aspect_ratio") val aspectRatio: Double? = null,
    @Json(name = "file_path") val filePath: String? = null,
    @Json(name = "height") val height: Int? = null,
    @Json(name = "iso_639_1") val iso6391: String? = null,
    @Json(name = "vote_average") val voteAverage: Int? = null,
    @Json(name = "vote_count") val voteCount: Int? = null,
    @Json(name = "width") val width: Int? = null
) : Parcelable