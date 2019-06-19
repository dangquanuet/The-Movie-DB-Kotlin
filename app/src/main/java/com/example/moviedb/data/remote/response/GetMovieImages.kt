package com.example.moviedb.data.remote.response

import com.example.moviedb.data.model.Backdrop
import com.example.moviedb.data.model.Poster

data class GetMovieImages(
    val id: Int? = null,
    val backdrops: List<Backdrop?>? = null,
    val posters: List<Poster?>? = null
) : BaseResponse()