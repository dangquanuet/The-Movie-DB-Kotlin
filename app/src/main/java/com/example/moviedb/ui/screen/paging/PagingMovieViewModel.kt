package com.example.moviedb.ui.screen.paging

import androidx.hilt.lifecycle.ViewModelInject
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.repository.UserRepository
import com.example.moviedb.data.source.BasePagingSource
import com.example.moviedb.data.source.MoviePagingSource
import com.example.moviedb.ui.base.BasePagingViewModel

class PagingMovieViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : BasePagingViewModel<Movie>() {

    override fun createPagingSource(): BasePagingSource<Movie> {
        return MoviePagingSource(
            userRepository
        )
    }

}