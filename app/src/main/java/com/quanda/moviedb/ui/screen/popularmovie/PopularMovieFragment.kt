package com.quanda.moviedb.ui.screen.popularmovie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.BR
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.quanda.moviedb.ui.base.fragment.BaseLoadMoreRefreshFragment

class PopularMovieFragment : BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, PopularMovieViewModel, Movie>(), PopularMovieNavigator {

    companion object {
        const val TYPE = "TYPE"

        const val TAG = "PopularMovieFragment"

        fun newInstance(type: Int) = PopularMovieFragment().apply {
            arguments = Bundle().apply {
                putInt(TYPE, type)
            }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: PopularMovieViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(
                PopularMovieViewModel::class.java)
                .apply {
                    navigator = this@PopularMovieFragment
                    mode = arguments?.getInt(TYPE) ?: Type.POPULAR.type
                }

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
            listItem.observe(this@PopularMovieFragment, Observer {
                adapter.submitList(it)
            })
            firstLoad()
        }
    }

    override fun goToMovieDetail(movie: Movie) {

    }

    enum class Type(val type: Int) {
        POPULAR(0), TOP_RATED(1)
    }
}