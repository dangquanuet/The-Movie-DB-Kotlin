package com.example.moviedb.ui.screen.splash

import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentSplashBinding
import com.example.moviedb.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    override val layoutId: Int = R.layout.fragment_splash

    override val viewModel: SplashViewModel by viewModel()

    private val handler = Handler()

    private val task = Runnable {
        findNavController().navigate(SplashFragmentDirections.toMain())
//        findNavController().navigate(SplashFragmentDirections.toMoviePager())
    }

    override fun onStart() {
        super.onStart()
        handler.postDelayed(task, 1000)
    }

    override fun onStop() {
        handler.removeCallbacks(task)
        super.onStop()
    }
}