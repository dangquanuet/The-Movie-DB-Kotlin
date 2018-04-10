package com.quanda.moviedb.ui.main.popularmovie

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.base.BaseViewHolderBinding
import com.quanda.moviedb.base.fragment.BaseDataLoadMoreRefreshFragment
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.FragmentBaseLoadmoreRefreshBinding

class PopularMovieFragment : BaseDataLoadMoreRefreshFragment<FragmentBaseLoadmoreRefreshBinding, PopularMovieViewModel, Movie>() {

    companion object {
        fun newInstance(): PopularMovieFragment {
            return PopularMovieFragment()
        }
    }

    override fun initViewModel(): PopularMovieViewModel {
        return PopularMovieViewModel(context!!, when (navigator) {
            is PopularMovieNavigator ->
                navigator as PopularMovieNavigator
            else -> object : PopularMovieNavigator {
                override fun finish() {
                    activity?.finish()
                }

                override fun onBackPressed() {
                    activity?.onBackPressed()
                }
            }
        })
    }

    override fun initData() {
        super.initData()
        binding.view = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter.set(mAdapter)

        viewModel.isDataLoading.set(true)
        viewModel.loadData(1)
    }

    override fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return PopularMovieAdapter(context!!, viewModel.listItem,
                object : BaseViewHolderBinding.OnItemCLickListener<Movie> {
                    override fun onItemClick(position: Int, data: Movie) {
                        // TODO
                    }
                }) as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(context, 2)
}