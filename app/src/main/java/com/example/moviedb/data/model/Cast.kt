package com.example.moviedb.data.model

import android.os.Parcelable
import com.example.moviedb.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Cast(
    @Json(name = "cast_id") val castId: String? = null,
    @Json(name = "character") val character: String? = null,
    @Json(name = "credit_id") val creditId: String? = null,
    @Json(name = "gender") val gender: Int? = null,
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "order") val order: Int? = null,
    @Json(name = "profile_path") val profilePath: String? = null
) : Parcelable {

    fun getFullProfilePath() =
        if (profilePath.isNullOrBlank()) null else BuildConfig.SMALL_IMAGE_URL + profilePath

}