package com.quanda.moviedb.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "spoken_language")
data class SpokenLanguage(val iso_639_1: String = "",
        @PrimaryKey(autoGenerate = false)
        val name: String) : Parcelable