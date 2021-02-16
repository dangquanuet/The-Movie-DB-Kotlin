package com.example.moviedb.ui.screen.paging

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.data.source.BasePagingSource
import com.example.moviedb.data.source.MoviePagingSource
import com.example.moviedb.ui.base.paging.BasePagingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagingMovieViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BasePagingViewModel<Movie>() {

    override fun createPagingSource(): BasePagingSource<Movie> {
        return MoviePagingSource(
            userRepository
        )
    }

}
