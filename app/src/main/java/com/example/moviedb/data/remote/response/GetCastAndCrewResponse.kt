package com.example.moviedb.data.remote.response

import com.example.moviedb.data.model.Cast
import com.example.moviedb.data.model.Crew

class GetCastAndCrewResponse(
    val id: Int? = null,
    val cast: List<Cast>? = null,
    val crew: List<Crew>? = null
) : BaseResponse()