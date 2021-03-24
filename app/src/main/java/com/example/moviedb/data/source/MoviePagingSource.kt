package com.example.moviedb.data.source

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.api.ApiParams
import com.example.moviedb.data.repository.UserRepository

class MoviePagingSource(
    private val userRepository: UserRepository
) : BasePagingSource<Movie>() {

    override suspend fun loadData(params: LoadParams<Int>): List<Movie>? {
        val hashMap = HashMap<String, String>()
        hashMap[ApiParams.PAGE] = (params.key ?: getFirstPage()).toString()
        hashMap[ApiParams.SORT_BY] = ApiParams.POPULARITY_DESC

        return userRepository.getMovieList(hashMap).results
    }
}

