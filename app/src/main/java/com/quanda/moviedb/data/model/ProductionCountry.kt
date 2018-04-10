package com.quanda.moviedb.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductionCountry(
        val iso_3166_1: String = "",
        val name: String = ""
) : Parcelable