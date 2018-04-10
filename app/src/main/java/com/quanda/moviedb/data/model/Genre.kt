package com.quanda.moviedb.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
        val id: Int = 0,
        val name: String = ""
) : Parcelable
