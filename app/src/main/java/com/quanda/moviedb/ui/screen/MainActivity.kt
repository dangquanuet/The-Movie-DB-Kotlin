package com.quanda.moviedb.ui.screen

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.quanda.moviedb.R
import com.quanda.moviedb.databinding.ActivityMainBinding
import com.quanda.moviedb.ui.base.activity.BaseActivity
import com.quanda.moviedb.ui.screen.main.MainFragment

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(), MainActivityNavigator {

    override val layoutId: Int
        get() = R.layout.activity_main

    override val viewModel: MainActivityViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(
                MainActivityViewModel::class.java).apply {
            navigator = this@MainActivity
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        supportFragmentManager.beginTransaction()
                .replace(R.id.parent, MainFragment.newInstance(), MainFragment.TAG)
                .commit()
    }

}
