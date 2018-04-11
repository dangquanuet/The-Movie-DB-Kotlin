package com.quanda.moviedb.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "production_company")
data class ProductionCompany(
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val logo_path: String = "",
        val name: String = "",
        val origin_country: String = ""
) : Parcelable

