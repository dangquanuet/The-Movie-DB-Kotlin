package com.example.moviedb.ui.screen.paging

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentPagingBinding
import com.example.moviedb.ui.base.BasePagingAdapter
import com.example.moviedb.ui.base.paging.BasePagingFragment
import com.example.moviedb.ui.base.getNavController
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

    override val swipeRefreshLayout: SwipeRefreshLayout
        get() = viewBinding.refreshLayout

    override val recyclerView: RecyclerView
        get() = viewBinding.recyclerView

    private fun toMovieDetail(movie: Movie) {
        getNavController()?.navigate(PagingMovieFragmentDirections.toGraphMovieDetail(movie))
    }
}
