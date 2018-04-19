package com.quanda.moviedb.ui.main.moviedetail

import android.arch.lifecycle.ViewModelProviders
import com.quanda.moviedb.R
import com.quanda.moviedb.base.activity.BaseDataLoadActivity
import com.quanda.moviedb.constants.BundleConstants
import com.quanda.moviedb.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : BaseDataLoadActivity<ActivityMovieDetailBinding, MovieDetailViewModel>(), MovieDetailNavigator {

    override fun getLayoutId(): Int {
        return R.layout.activity_movie_detail
    }

    override fun initViewModel(): MovieDetailViewModel {
        return ViewModelProviders.of(this,
                MovieDetailViewModel.CustomFactory(application, this)).get(
                MovieDetailViewModel::class.java)
    }

    override fun initData() {
        super.initData()
        binding.apply {
            viewModel = this@MovieDetailActivity.viewModel
        }

        intent.extras?.apply {
            viewModel.movie.value = getParcelable(BundleConstants.MOVIE)
        }
    }
}