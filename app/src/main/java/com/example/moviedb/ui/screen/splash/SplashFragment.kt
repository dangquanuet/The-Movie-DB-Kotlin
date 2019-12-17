package com.example.moviedb.ui.screen.splash

import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentSplashBinding
import com.example.moviedb.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_splash

    override val viewModel: SplashViewModel by viewModel()

    private val handler = Handler()

    private val task = Runnable {
//        findNavController().navigate(SplashFragmentDirections.splashToMain())
        findNavController().navigate(SplashFragmentDirections.toMoviePager())
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(task, 1000)
    }

    override fun onPause() {
        handler.removeCallbacks(task)
        super.onPause()
    }
}