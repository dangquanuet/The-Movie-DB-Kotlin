package com.example.moviedb.ui.screen.paging

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentPagingBinding
import com.example.moviedb.ui.base.BasePagingAdapter
import com.example.moviedb.ui.base.BasePagingFragment
import com.example.moviedb.ui.base.getNavController
import com.example.moviedb.ui.screen.paged.PagedMovieFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingMovieFragment :
    BasePagingFragment<FragmentPagingBinding, PagingMovieViewModel, Movie>() {

    override val viewModel: PagingMovieViewModel by viewModels()

    override val pagingAdapter: BasePagingAdapter<Movie, out ViewDataBinding> by lazy {
        PagingMovieAdapter(
            itemClickListener = {
                toMovieDetail(it)
            }
        )
    }

    private fun toMovieDetail(movie: Movie) {
        getNavController()?.navigate(PagedMovieFragmentDirections.toGraphMovieDetail(movie))
    }
}