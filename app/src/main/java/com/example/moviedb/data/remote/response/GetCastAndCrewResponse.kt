package com.example.moviedb.data.remote.response

import com.example.moviedb.data.model.Cast
import com.example.moviedb.data.model.Crew
import com.squareup.moshi.Json

class GetCastAndCrewResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "cast") val cast: List<Cast>? = null,
    @Json(name = "crew") val crew: List<Crew>? = null
) : BaseResponse()