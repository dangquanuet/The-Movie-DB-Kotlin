package com.example.moviedb.ui.screen.favoritemovie

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.example.moviedb.ui.base.BaseLoadMoreRefreshFragment
import com.example.moviedb.ui.screen.popularmovie.PopularMovieAdapter
import kotlinx.android.synthetic.main.fragment_loadmore_refresh.*
import org.koin.androidx.viewmodel.ext.viewModel

class FavoriteMovieFragment :
    BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, FavoriteMovieViewModel, Movie>() {

    companion object {
        const val TAG = "FavoriteMovieFragment"

        fun newInstance() = FavoriteMovieFragment()
    }

    override val viewModel: FavoriteMovieViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = PopularMovieAdapter(
            itemClickListener = {
                toMovieDetail(it)
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
            loadData(getFirstPage())
        }
    }

    fun loadData() {
        viewModel.loadData(viewModel.getFirstPage())
    }

    fun toMovieDetail(movie: Movie) {
        findNavController().navigate(
            FavoriteMovieFragmentDirections.toMovieDetail(movie)
        )
    }
}