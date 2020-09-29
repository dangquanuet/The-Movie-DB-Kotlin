package com.example.moviedb.ui.screen.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentSplashBinding
import com.example.moviedb.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    override val layoutId: Int = R.layout.fragment_splash

    override val viewModel: SplashViewModel by viewModels()

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onStart() {
        super.onStart()
        activityScope.launch {
            delay(1000)
            navigateToOther()
        }
    }

    fun navigateToOther() {
        findNavController().navigate(
            when (2) {
                0 -> SplashFragmentDirections.toMovieListPager()
                1 -> SplashFragmentDirections.toPagedMovie()
                else -> SplashFragmentDirections.toMain()
            }
        )
    }

    override fun onStop() {
        activityScope.cancel()
        super.onStop()
    }
}