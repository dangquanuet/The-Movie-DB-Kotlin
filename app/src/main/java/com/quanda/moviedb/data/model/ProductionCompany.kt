package com.quanda.moviedb.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductionCompany(
        val id: Int = 0,
        val logo_path: String = "",
        val name: String = "",
        val origin_country: String = ""
) : Parcelable

