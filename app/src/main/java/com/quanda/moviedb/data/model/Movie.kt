package com.quanda.moviedb.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable