package com.quanda.moviedb.ui.main

import android.content.Intent
import android.os.Handler
import com.quanda.moviedb.R
import com.quanda.moviedb.base.activity.BaseDataLoadActivity
import com.quanda.moviedb.databinding.ActivityMainBinding
import com.quanda.moviedb.ui.movie.MovieListActivity

class MainActivity : BaseDataLoadActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): MainViewModel {
        return MainViewModel(this, this)
    }

    override fun initData() {
        super.initData()
        binding.viewModel = viewModel

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, MovieListActivity::class.java))
        }, 500)
    }
}
