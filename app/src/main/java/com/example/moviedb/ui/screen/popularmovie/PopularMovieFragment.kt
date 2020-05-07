package com.example.moviedb.ui.screen.popularmovie

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.example.moviedb.ui.base.BaseLoadMoreRefreshFragment
import kotlinx.android.synthetic.main.fragment_loadmore_refresh.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularMovieFragment :
    BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, PopularMovieViewModel, Movie>() {

    /*companion object {
        const val TAG = "PopularMovieFragment"
        const val TYPE = "TYPE"

        fun newInstance(type: Int) = PopularMovieFragment().apply {
            arguments = Bundle().apply {
                putInt(TYPE, type)
            }
        }
    }*/

    override val viewModel: PopularMovieViewModel by viewModel()

    private val args: PopularMovieFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = PopularMovieAdapter(
            itemClickListener = { toMovieDetail(it) }
        )

        container?.setBackgroundColor(Color.BLACK)
        recycler_view?.apply {
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

    private fun toMovieDetail(movie: Movie) {
        findNavController().navigate(
            PopularMovieFragmentDirections.toMovieDetail(movie)
        )
    }
}