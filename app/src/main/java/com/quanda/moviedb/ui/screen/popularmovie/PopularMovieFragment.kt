package com.quanda.moviedb.ui.screen.popularmovie

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.data.constants.BundleConstants
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.FragmentBaseLoadmoreRefreshBinding
import com.quanda.moviedb.ui.base.fragment.BaseLoadMoreRefreshFragment

class PopularMovieFragment : BaseLoadMoreRefreshFragment<FragmentBaseLoadmoreRefreshBinding, PopularMovieViewModel, Movie>(), PopularMovieNavigator {

    companion object {
        fun newInstance(type: Int): PopularMovieFragment {
            val popularMovieFragment = PopularMovieFragment()
            val bundle = Bundle()
            bundle.putInt(BundleConstants.TYPE, type)
            popularMovieFragment.arguments = bundle
            return popularMovieFragment
        }
    }

    override val viewModel: PopularMovieViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(
                PopularMovieViewModel::class.java)
                .apply {
                    navigator = this@PopularMovieFragment
                    mode = arguments?.getInt(
                            BundleConstants.TYPE) ?: TYPE.POPULAR.type
                }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
        return PopularMovieAdapter(context!!,
                viewModel.listItem,
                viewModel.itemCLickListener) as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun initLayoutManager() = GridLayoutManager(context, 2)

    override fun goToMovieDetail(movie: Movie) {

    }

    enum class TYPE(val type: Int) {
        POPULAR(0), TOP_RATED(1)
    }
}