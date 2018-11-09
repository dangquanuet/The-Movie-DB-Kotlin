package com.quanda.moviedb.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tv")
class Tv(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val original_name: String? = "",
    val name: String? = "",
    val popularity: Double? = 0.0,
    val vote_count: Int? = 0,
    val first_air_date: String? = "",
    val backdrop_path: String? = "",
    val original_language: String? = "",
    val vote_average: Double? = 0.0,
    val overview: String? = "",
    val poster_path: String? = ""
) : Parcelable