package com.example.moviedb.tests.support

import com.example.moviedb.data.model.Cast
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetCastAndCrewResponse

object TestData {

    fun movie(
        id: String = "1",
        title: String = "Test Movie",
        isFavorite: Boolean = false,
    ) = Movie(id = id, title = title, isFavorite = isFavorite)

    fun cast(name: String = "Actor") = Cast(name = name)

    fun castResponse(vararg cast: Cast) =
        GetCastAndCrewResponse(cast = cast.toList())
}
