package com.example.moviedb.ui.screen

import androidx.activity.viewModels
import com.example.moviedb.R
import com.example.moviedb.databinding.ActivityMainBinding
import com.example.moviedb.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override val viewModel: MainActivityViewModel by viewModels()

    override val layoutId: Int = R.layout.activity_main

}
