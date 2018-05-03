package com.quanda.moviedb.ui.screen.main.popularmovie

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.ui.base.fragment.BaseDataLoadMoreRefreshFragment
import com.quanda.moviedb.data.constants.BundleConstants
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
        return ViewModelProviders.of(this,
                PopularMovieViewModel.CustomFactory(activity?.application!!,
                        navigator as PopularMovieNavigator,
                        arguments?.getInt(
                                BundleConstants.TYPE)
                                ?: PopularMovieFragment.TYPE.POPULAR.type)).get(
                PopularMovieViewModel::class.java)
    }

    override fun initData() {
        super.initData()
        binding.apply {
            root.setBackgroundColor(Color.BLACK)
            view = this@PopularMovieFragment
            viewModel = this@PopularMovieFragment.viewModel
            recyclerView.adapter.value = adapter
            recyclerView.layoutManager.value = layoutManager
        }

        if (viewModel.listItem.isEmpty()) {
            viewModel.apply {
                isDataLoading.value = true
                loadData(1)
            }
        }
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