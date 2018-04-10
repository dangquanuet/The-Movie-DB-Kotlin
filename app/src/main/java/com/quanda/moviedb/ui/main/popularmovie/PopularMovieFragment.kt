package com.quanda.moviedb.ui.main.popularmovie

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.base.fragment.BaseDataLoadMoreRefreshFragment
import com.quanda.moviedb.constants.BundleConstants
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.FragmentBaseLoadmoreRefreshBinding

class PopularMovieFragment : BaseDataLoadMoreRefreshFragment<FragmentBaseLoadmoreRefreshBinding, PopularMovieViewModel, Movie>() {

    companion object {
        fun newInstance(type: Int): PopularMovieFragment {
            val popularMovieFragment = PopularMovieFragment()
            val bundle = Bundle()
            bundle.putInt(BundleConstants.TYPE, type)
            popularMovieFragment.arguments = bundle
            return popularMovieFragment
        }
    }

    override fun initViewModel(): PopularMovieViewModel {
        return PopularMovieViewModel(context!!,
                navigator as PopularMovieNavigator,
                arguments?.getInt(BundleConstants.TYPE) ?: PopularMovieFragment.TYPE.POPULAR.type)
    }

    override fun initData() {
        super.initData()
        binding.root.setBackgroundColor(Color.BLACK)
        binding.view = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter.set(adapter)
        binding.recyclerView.layoutManager.set(layoutManager)

        viewModel.isDataLoading.set(true)
        viewModel.loadData(1)
    }

    override fun initAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return PopularMovieAdapter(context!!, viewModel.listItem,
                viewModel.itemCLickListener) as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun initLayoutManager() = GridLayoutManager(context, 2)

    enum class TYPE(val type: Int) {
        POPULAR(0), TOP_RATED(1)
    }
}