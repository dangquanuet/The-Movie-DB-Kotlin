package com.example.moviedb.data.model

import android.os.Parcelable
import com.example.moviedb.BuildConfig
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Cast(
    val cast_id: String? = null,
    val character: String? = null,
    val credit_id: String? = null,
    val gender: Int? = null,
    val id: String? = null,
    val name: String? = null,
    val order: Int? = null,
    val profile_path: String? = null
) : Parcelable {

    fun getFullProfilePath() =
        if (profile_path.isNullOrBlank()) null else BuildConfig.SMALL_IMAGE_URL + profile_path

}