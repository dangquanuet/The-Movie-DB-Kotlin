package com.example.moviedb.data.remote.response

import com.example.moviedb.data.model.Backdrop
import com.example.moviedb.data.model.Poster
import com.squareup.moshi.Json

data class GetMovieImages(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "backdrops") val backdrops: List<Backdrop?>? = null,
    @Json(name = "posters") val posters: List<Poster?>? = null
) : BaseResponse()