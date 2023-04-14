package com.example.moviedb.ui.screen.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentSplashBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.ui.base.getNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, BaseViewModel>() {

    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.splashViewStateFlow.collect { state ->
                    when (state) {
                        is SplashViewState.Idle -> {
                            // do nothing
                        }

                        is SplashViewState.NavigateToHome -> {
                            navigateToHome()
                        }
                    }
                }
            }
        }
        viewModel.showSplash()
    }

    private fun navigateToHome() {
        getNavController()?.navigate(
            when (1) {
                0 -> SplashFragmentDirections.toMovieListPager()
                1 -> SplashFragmentDirections.toPopularMovie()
                else -> SplashFragmentDirections.toPagingMovie()
            }
        )
    }
}
