package com.quanda.moviedb.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        val id: Int = 0,
        val email: String = ""
) : Parcelable