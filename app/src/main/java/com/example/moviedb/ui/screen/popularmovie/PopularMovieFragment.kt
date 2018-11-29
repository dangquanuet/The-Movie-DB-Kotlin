package com.example.moviedb.ui.screen.popularmovie

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.data.constants.MovieListType
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.example.moviedb.ui.base.BaseLoadMoreRefreshFragment
import com.example.moviedb.ui.screen.moviedetail.MovieDetailFragment
import kotlinx.android.synthetic.main.fragment_loadmore_refresh.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularMovieFragment :
    BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, PopularMovieViewModel, Movie>() {

    companion object {
        const val TAG = "PopularMovieFragment"
        const val TYPE = "TYPE"

        fun newInstance(type: Int) = PopularMovieFragment().apply {
            arguments = Bundle().apply {
                putInt(TYPE, type)
            }
        }
    }

    override val viewModel by viewModel<PopularMovieViewModel>()

    // user this for share viewModel between activity and fragment
//    override val viewModel by sharedViewModel<MainActivityViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.apply {
            mode.value = arguments?.getInt(TYPE) ?: MovieListType.POPULAR.type
        }

        val adapter = PopularMovieAdapter(
            itemClickListener = { goToMovieDetail(it) }
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

    fun goToMovieDetail(movie: Movie) {
        addChildFragment(
            containerViewId = container.id,
            fragment = MovieDetailFragment.newInstance(movie),
            TAG = MovieDetailFragment.TAG,
            addToBackStack = true
        )
    }
}