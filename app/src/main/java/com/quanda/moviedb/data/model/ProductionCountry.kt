package com.quanda.moviedb.data.model

import android.os.Parcel
import android.os.Parcelable


data class ProductionCountry(
        val iso_3166_1: String = "",
        val name: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(iso_3166_1)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductionCountry> {
        override fun createFromParcel(parcel: Parcel): ProductionCountry {
            return ProductionCountry(parcel)
        }

        override fun newArray(size: Int): Array<ProductionCountry?> {
            return arrayOfNulls(size)
        }
    }
}