package com.quanda.moviedb.ui.screen.favoritemovie

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.quanda.moviedb.ui.base.BaseLoadMoreRefreshFragment
import com.quanda.moviedb.ui.screen.moviedetail.MovieDetailFragment
import com.quanda.moviedb.ui.screen.popularmovie.PopularMovieAdapter
import kotlinx.android.synthetic.main.fragment_loadmore_refresh.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMovieFragment :
    BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, FavoriteMovieViewModel, Movie>() {

    companion object {
        const val TAG = "FavoriteMovieFragment"

        fun newInstance() = FavoriteMovieFragment()
    }

    override val viewModel by viewModel<FavoriteMovieViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = PopularMovieAdapter(
            itemClickListener = {
                goToMovieDetail(it)
            }
        )

        container.setBackgroundColor(Color.BLACK)
        recycler_view.apply {
            layoutManager = GridLayoutManager(context, 2)
            this.adapter = adapter
        }

        viewModel.apply {
            listItem.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
            firstLoad()
        }
    }

    fun loadData() {
        viewModel.firstLoad()
    }

    fun goToMovieDetail(movie: Movie) {
        addChildFragment(
            containerViewId = container.id,
            fragment = MovieDetailFragment.newInstance(movie),
            TAG = MovieDetailFragment.TAG,
            addToBackStack = true
        )
    }
}