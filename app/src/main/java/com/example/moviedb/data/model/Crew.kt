package com.example.moviedb.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Crew(
    @Json(name = "credit_id") val creditId: String? = null,
    @Json(name = "department") val department: String? = null,
    @Json(name = "gender") val gender: Int? = null,
    @Json(name = "id") val id: String? = null,
    @Json(name = "job") val job: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "profile_path") val profilePath: String? = null
) : Parcelable