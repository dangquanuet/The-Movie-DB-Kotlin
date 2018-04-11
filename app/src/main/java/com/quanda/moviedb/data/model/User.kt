package com.quanda.moviedb.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User(
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val email: String = ""
) : Parcelable