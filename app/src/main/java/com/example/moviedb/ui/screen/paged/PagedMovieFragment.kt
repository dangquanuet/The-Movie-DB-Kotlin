package com.example.moviedb.ui.screen.paged

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentPagedRefreshBinding
import com.example.moviedb.ui.base.BasePagedRefreshFragment
import com.example.moviedb.ui.base.getNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagedMovieFragment :
    BasePagedRefreshFragment<FragmentPagedRefreshBinding, PagedMovieViewModel, Movie>() {

    override val viewModel: PagedMovieViewModel by viewModels()

    override val pagedListAdapter by lazy {
        PagedMovieAdapter(
            itemClickListener = { toMovieDetail(it) }
        )
    }

    override val swipeRefreshLayout: SwipeRefreshLayout
        get() = viewBinding.refreshLayout

    override val recyclerView: RecyclerView
        get() = viewBinding.recyclerView

    override fun getLayoutManager() = GridLayoutManager(context, 2)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.container.setBackgroundColor(Color.BLACK)
    }

    private fun toMovieDetail(movie: Movie) {
        getNavController()?.navigate(PagedMovieFragmentDirections.toGraphMovieDetail(movie))
    }
}
