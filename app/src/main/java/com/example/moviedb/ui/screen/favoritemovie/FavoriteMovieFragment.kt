package com.example.moviedb.ui.screen.favoritemovie

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.example.moviedb.ui.base.BaseListAdapter
import com.example.moviedb.ui.base.loadmorerefresh.BaseLoadMoreRefreshFragment
import com.example.moviedb.ui.base.getNavController
import com.example.moviedb.ui.screen.popularmovie.PopularMovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMovieFragment :
    BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, FavoriteMovieViewModel, Movie>() {

    override val viewModel: FavoriteMovieViewModel by viewModels()

    override val listAdapter: BaseListAdapter<Movie, out ViewDataBinding> by lazy {
        PopularMovieAdapter(
            itemClickListener = {
                toMovieDetail(it)
            }
        )
    }

    override val swipeRefreshLayout: SwipeRefreshLayout
        get() = viewBinding.refreshLayout

    override val recyclerView: RecyclerView
        get() = viewBinding.recyclerView

    override fun getLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(context, 2)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.container.setBackgroundColor(Color.BLACK)
    }

    fun loadData() {
        viewModel.loadData(viewModel.getFirstPage())
    }

    private fun toMovieDetail(movie: Movie) {
        getNavController()?.navigate(
            FavoriteMovieFragmentDirections.toGraphMovieDetail(movie)
        )
    }
}
