package com.example.moviedb.ui.screen.paged

import androidx.hilt.lifecycle.ViewModelInject
import androidx.paging.PageKeyedDataSource
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.ApiParams
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.ui.base.BasePagedRefreshViewModel

class PagedMovieViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : BasePagedRefreshViewModel<Movie>() {

    override suspend fun loadData(
        loadInitialParams: PageKeyedDataSource.LoadInitialParams<Int>?,
        loadParams: PageKeyedDataSource.LoadParams<Int>?
    ): List<Movie> {
        val apiParams = HashMap<String, String>()
        apiParams[ApiParams.PAGE] = (loadParams?.key ?: firstPage).toString()

        return userRepository.getMovieList(apiParams).results?.toList() ?: listOf()
    }

    override val pageSize: Int = 20

}