package com.example.moviedb.ui.screen.favoritemovie

import android.graphics.Color
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.example.moviedb.ui.base.BaseListAdapter
import com.example.moviedb.ui.base.BaseLoadMoreRefreshFragment
import com.example.moviedb.ui.screen.popularmovie.PopularMovieAdapter
import kotlinx.android.synthetic.main.fragment_loadmore_refresh.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMovieFragment :
    BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, FavoriteMovieViewModel, Movie>() {

    override val viewModel: FavoriteMovieViewModel by viewModel()

    override val listAdapter: BaseListAdapter<Movie, out ViewDataBinding> by lazy {
        PopularMovieAdapter(
            itemClickListener = {
                toMovieDetail(it)
            }
        )
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(context, 2)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        container.setBackgroundColor(Color.BLACK)
    }

    fun loadData() {
        viewModel.loadData(viewModel.getFirstPage())
    }

    private fun toMovieDetail(movie: Movie) {
        findNavController().navigate(
            FavoriteMovieFragmentDirections.toGraphMovieDetail(movie)
        )
    }
}