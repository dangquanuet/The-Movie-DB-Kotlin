package com.example.moviedb.ui.navigation.favoritecontainer

import androidx.fragment.app.viewModels
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentFavoriteContainerBinding
import com.example.moviedb.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteContainerFragment :
    BaseFragment<FragmentFavoriteContainerBinding, FavoriteContainerVIewModel>() {

    override val layoutId: Int = R.layout.fragment_favorite_container

    override val viewModel: FavoriteContainerVIewModel by viewModels()

}