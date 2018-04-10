package com.quanda.moviedb.ui.main.moviedetail

import com.quanda.moviedb.R
import com.quanda.moviedb.base.activity.BaseDataLoadActivity
import com.quanda.moviedb.constants.BundleConstants
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : BaseDataLoadActivity<ActivityMovieDetailBinding, MovieDetailViewModel>(), MovieDetailNavigator {

    override fun getLayoutId(): Int {
        return R.layout.activity_movie_detail
    }

    override fun initViewModel(): MovieDetailViewModel {
        return MovieDetailViewModel(this, this)
    }

    override fun initData() {
        super.initData()
        binding.viewModel = viewModel

        val bundle = intent.extras
        if (bundle != null) {
            val movie = bundle.getParcelable<Movie>(BundleConstants.MOVIE)
            viewModel.movie.set(bundle.getParcelable(BundleConstants.MOVIE))
        }
    }
}