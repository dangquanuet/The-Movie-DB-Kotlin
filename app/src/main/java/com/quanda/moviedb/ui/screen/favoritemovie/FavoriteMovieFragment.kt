package com.quanda.moviedb.ui.screen.favoritemovie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.quanda.moviedb.ui.base.fragment.BaseLoadMoreRefreshFragment
import com.quanda.moviedb.ui.screen.popularmovie.PopularMovieAdapter

class FavoriteMovieFragment : BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, FavoriteMovieViewModel, Movie>() {

    companion object {
        const val TAG = "FavoriteMovieFragment"

        fun newInstance() = FavoriteMovieFragment()
    }

    override val viewModel: FavoriteMovieViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(
                FavoriteMovieViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = PopularMovieAdapter(itemClickListener = { goToMovieDetail(it) })
        viewBinding.apply {
            root.setBackgroundColor(Color.BLACK)
            recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                this.adapter = adapter
            }
        }
        viewModel.apply {
            listItem.observe(this@FavoriteMovieFragment, Observer {
                adapter.submitList(it)
            })
            firstLoad()
        }
    }

    fun goToMovieDetail(movie: Movie) {

    }
}