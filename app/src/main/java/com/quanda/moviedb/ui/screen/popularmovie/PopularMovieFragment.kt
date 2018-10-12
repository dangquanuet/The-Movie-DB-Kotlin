package com.quanda.moviedb.ui.screen.popularmovie

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.quanda.moviedb.data.constants.MovieListType
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.quanda.moviedb.ui.base.BaseLoadMoreRefreshFragment
import com.quanda.moviedb.ui.screen.moviedetail.MovieDetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularMovieFragment : BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, PopularMovieViewModel, Movie>() {

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

        viewBinding.apply {
            root.setBackgroundColor(Color.BLACK)
            recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                this.adapter = adapter
            }
        }

        viewModel.apply {
            listItem.observe(this@PopularMovieFragment, Observer {
                adapter.submitList(it)
            })
            firstLoad()
        }
    }

    fun goToMovieDetail(movie: Movie) {
        replaceChildFragment(
                containerViewId = viewBinding.parent.id,
                fragment = MovieDetailFragment.newInstance(movie),
                TAG = MovieDetailFragment.TAG,
                addToBackStack = true)
    }
}