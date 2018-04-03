package com.quanda.moviedb.ui.main

import com.quanda.moviedb.R
import com.quanda.moviedb.base.activity.BaseDataLoadActivity
import com.quanda.moviedb.databinding.ActivityMainBinding

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
    }
}
