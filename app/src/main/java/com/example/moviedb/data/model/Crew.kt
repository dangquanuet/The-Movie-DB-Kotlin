package com.example.moviedb.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crew(
    val credit_id: String? = null,
    val department: String? = null,
    val gender: Int? = null,
    val id: String? = null,
    val job: String? = null,
    val name: String? = null,
    val profile_path: String? = null
) : Parcelable