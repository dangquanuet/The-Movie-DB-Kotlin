package com.quanda.moviedb.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "production_country")
data class ProductionCountry(
        val iso_3166_1: String = "",
        @PrimaryKey(autoGenerate = false)
        val name: String
) : Parcelable