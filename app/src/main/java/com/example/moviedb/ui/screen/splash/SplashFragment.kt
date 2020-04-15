package com.example.moviedb.ui.screen.splash

import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentSplashBinding
import com.example.moviedb.ui.base.BaseFragment
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Runnable

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    override val layoutId: Int = R.layout.fragment_splash

    override val viewModel: SplashViewModel by viewModel()

    private val handler = Handler()

    private val task = Runnable {
//        findNavController().navigate(SplashFragmentDirections.toMain())
        navigateToOther()
    }

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onStart() {
        super.onStart()
        activityScope.launch {
            delay(1000)
            navigateToOther()
        }
//        handler.postDelayed(task, 1000)
    }

    fun navigateToOther() {
        findNavController().navigate(
            when (1) {
                0 -> SplashFragmentDirections.toMovieListPager()
                1 -> SplashFragmentDirections.toPagedMovie()
                else -> SplashFragmentDirections.toMain()
            }
        )
    }

    override fun onStop() {
        activityScope.cancel()
//        handler.removeCallbacks(task)
        super.onStop()
    }
}