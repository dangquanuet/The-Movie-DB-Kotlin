package com.quanda.moviedb.ui.screen.movie

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.quanda.moviedb.BR
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.quanda.moviedb.ui.base.BaseLoadMoreRefreshFragment

class MovieListFragment : BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, MovieListViewModel, Movie>() {

    companion object {
        const val TAG = "MovieListFragment"

        fun newInstance() = MovieListFragment()
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: MovieListViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = MovieListAdapter(itemClickListener = { goToMovieDetail(it) })
        viewBinding.apply {
            root.setBackgroundColor(Color.BLACK)
            recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                this.adapter = adapter
            }
        }
        viewModel.apply {
            listItem.observe(this@MovieListFragment, Observer {
                adapter.submitList(it)
            })
            firstLoad()
        }
    }

    fun goToMovieDetail(movie: Movie) {

    }
}