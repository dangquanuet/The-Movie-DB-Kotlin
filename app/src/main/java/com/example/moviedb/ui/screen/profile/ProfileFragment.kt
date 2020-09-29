package com.example.moviedb.ui.screen.profile

import androidx.fragment.app.viewModels
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentProfileBinding
import com.example.moviedb.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_profile

    override val viewModel: ProfileViewModel by viewModels()

}