package com.example.moviedb.ui.screen.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentSplashBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.base.getNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    override val layoutId: Int = R.layout.fragment_splash

    override val viewModel: SplashViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            delay(1000)
            navigateToOther()
        }
    }

    private fun navigateToOther() {
        getNavController()?.navigate(
            when (2) {
                0 -> SplashFragmentDirections.toMovieListPager()
                1 -> SplashFragmentDirections.toPagedMovie()
                2 -> SplashFragmentDirections.toPagingMovie()
                else -> SplashFragmentDirections.toMain()
            }
        )
    }
}