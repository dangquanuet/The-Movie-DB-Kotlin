package com.quanda.moviedb.ui.main.moviedetail

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.view.View
import com.quanda.moviedb.R
import com.quanda.moviedb.base.activity.BaseDataLoadActivity
import com.quanda.moviedb.constants.BundleConstants
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : BaseDataLoadActivity<ActivityMovieDetailBinding, MovieDetailViewModel>(), MovieDetailNavigator {

    override fun getLayoutId() = R.layout.activity_movie_detail

    override fun initViewModel(): MovieDetailViewModel {
        return ViewModelProviders.of(this,
                MovieDetailViewModel.CustomFactory(application, this)).get(
                MovieDetailViewModel::class.java)
    }

    override fun initData() {
        super.initData()
        binding.apply {
            viewModel = this@MovieDetailActivity.viewModel
            favoriteListener = View.OnClickListener { this@MovieDetailActivity.viewModel.favoriteMovie() }
        }

        intent.extras?.apply {
            getParcelable<Movie>(BundleConstants.MOVIE)?.apply {
                viewModel.updateNewMovie(this)
            }
        }
    }

    override fun onBackPressed() {
        if (viewModel.favoriteChanged.value == true) {
            setResult(Activity.RESULT_OK)
        }
        super.onBackPressed()
    }
}