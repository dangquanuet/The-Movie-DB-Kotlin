package com.example.moviedb.ui.navigation.popularcontainer

import androidx.fragment.app.viewModels
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentPopularContainerBinding
import com.example.moviedb.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularContainerFragment :
    BaseFragment<FragmentPopularContainerBinding, PopularContainerViewModel>() {

    override val layoutId: Int = R.layout.fragment_popular_container

    override val viewModel: PopularContainerViewModel by viewModels()

}