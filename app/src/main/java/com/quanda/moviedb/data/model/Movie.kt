package com.quanda.moviedb.data.model

import android.os.Parcel
import android.os.Parcelable


data class Movie(
        val adult: Boolean = false,
        val backdrop_path: String = "",
        val budget: Int = 0,
        val genres: List<Genre> = listOf(),
        val homepage: String = "",
        val id: Int = 0,
        val imdb_id: String = "",
        val original_language: String = "",
        val original_title: String = "",
        val overview: String = "",
        val popularity: Double = 0.0,
        val poster_path: String = "",
        val production_companies: List<ProductionCompany> = listOf(),
        val production_countries: List<ProductionCountry> = listOf(),
        val release_date: String = "",
        val revenue: Int = 0,
        val runtime: Int = 0,
        val spoken_languages: List<SpokenLanguage> = listOf(),
        val status: String = "",
        val tagline: String = "",
        val title: String = "",
        val video: Boolean = false,
        val vote_average: Double = 0.0,
        val vote_count: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readInt(),
            parcel.createTypedArrayList(Genre),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.createTypedArrayList(ProductionCompany),
            parcel.createTypedArrayList(ProductionCountry),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.createTypedArrayList(SpokenLanguage),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readDouble(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(backdrop_path)
        parcel.writeInt(budget)
        parcel.writeTypedList(genres)
        parcel.writeString(homepage)
        parcel.writeInt(id)
        parcel.writeString(imdb_id)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(overview)
        parcel.writeDouble(popularity)
        parcel.writeString(poster_path)
        parcel.writeTypedList(production_companies)
        parcel.writeTypedList(production_countries)
        parcel.writeString(release_date)
        parcel.writeInt(revenue)
        parcel.writeInt(runtime)
        parcel.writeTypedList(spoken_languages)
        parcel.writeString(status)
        parcel.writeString(tagline)
        parcel.writeString(title)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeDouble(vote_average)
        parcel.writeInt(vote_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}