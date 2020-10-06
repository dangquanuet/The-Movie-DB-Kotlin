package com.example.moviedb.ui.screen.popularmovie

import android.graphics.Color
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.example.moviedb.ui.base.BaseListAdapter
import com.example.moviedb.ui.base.BaseLoadMoreRefreshFragment
import com.example.moviedb.ui.base.getNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_loadmore_refresh.*

@AndroidEntryPoint
class PopularMovieFragment :
    BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, PopularMovieViewModel, Movie>() {

    override val viewModel: PopularMovieViewModel by viewModels()

    override val listAdapter: BaseListAdapter<Movie, out ViewDataBinding> by lazy {
        PopularMovieAdapter(
            itemClickListener = { toMovieDetail(it) }
        )
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(context, 2)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        container?.setBackgroundColor(Color.BLACK)
    }

    private fun toMovieDetail(movie: Movie) {
        getNavController()?.navigate(
            PopularMovieFragmentDirections.toGraphMovieDetail(movie)
        )
    }
}