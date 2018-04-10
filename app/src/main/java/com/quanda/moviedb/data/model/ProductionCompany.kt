package com.quanda.moviedb.data.model

import android.os.Parcel
import android.os.Parcelable

data class ProductionCompany(
        val id: Int = 0,
        val logo_path: String = "",
        val name: String = "",
        val origin_country: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(logo_path)
        parcel.writeString(name)
        parcel.writeString(origin_country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductionCompany> {
        override fun createFromParcel(parcel: Parcel): ProductionCompany {
            return ProductionCompany(parcel)
        }

        override fun newArray(size: Int): Array<ProductionCompany?> {
            return arrayOfNulls(size)
        }
    }
}

