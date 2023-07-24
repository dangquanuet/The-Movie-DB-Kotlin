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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, BaseViewModel>() {

    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
        viewModel.showSplash()
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.splashViewStateFlow.collectLatest { state ->
                    when (state) {
                        SplashViewState.Idle -> {
                            // do nothing
                        }

                        SplashViewState.NavigateToHome -> {
                            navigateToHome()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        getNavController()?.navigate(
            when (2) {
                0 -> SplashFragmentDirections.toMovieListPager()
                1 -> SplashFragmentDirections.toPopularMovie()
                2 -> SplashFragmentDirections.toComposeActivity()
                else -> SplashFragmentDirections.toPagingMovie()
            }
        )
    }
}
