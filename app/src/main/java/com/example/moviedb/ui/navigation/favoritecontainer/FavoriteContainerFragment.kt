package com.example.moviedb.ui.navigation.favoritecontainer

import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentFavoriteContainerBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.viewModel

class FavoriteContainerFragment :
    BaseFragment<FragmentFavoriteContainerBinding, BaseViewModel>() {

    override val layoutId: Int = R.layout.fragment_favorite_container

    override val viewModel: BaseViewModel by viewModel()

}