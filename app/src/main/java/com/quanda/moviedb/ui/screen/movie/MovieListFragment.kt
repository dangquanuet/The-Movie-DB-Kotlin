package com.quanda.moviedb.ui.screen.movie

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.FragmentLoadmoreRefreshBinding
import com.quanda.moviedb.ui.base.BaseViewHolderBinding
import com.quanda.moviedb.ui.base.fragment.BaseLoadMoreRefreshFragment

class MovieListFragment : BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, MovieListViewModel, Movie>(), MovieListNavigator {

    companion object {
        fun newInstance() = MovieListFragment()
    }

    override val viewModel: MovieListViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
                .apply {
                    navigator = this@MovieListFragment
                }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            view = this@MovieListFragment
            viewModel = this@MovieListFragment.viewModel
            recyclerView.adapter.value = adapter
        }

        viewModel.apply {
            isDataLoading.value = true
            loadData(1)
        }
    }

    override fun initAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return MovieListAdapter(viewModel.listItem,
                object : BaseViewHolderBinding.OnItemCLickListener<Movie> {
                    override fun onItemClick(position: Int, data: Movie) {
                        // TODO
                    }
                }) as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun initLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(context, 2)
}