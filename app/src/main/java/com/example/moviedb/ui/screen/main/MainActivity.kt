package com.example.moviedb.ui.screen.main

import androidx.activity.viewModels
import com.example.moviedb.R
import com.example.moviedb.databinding.ActivityMainBinding
import com.example.moviedb.ui.base.BaseActivity
import com.example.moviedb.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {

    override val viewModel: BaseViewModel by viewModels()
    override val layoutId: Int = R.layout.activity_main

}
