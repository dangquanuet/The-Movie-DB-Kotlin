package com.example.moviedb.ui.screen.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentMainBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.navigation.KeepStateNavigator
import com.example.moviedb.ui.screen.permission.PermissionFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    override val layoutId: Int = R.layout.fragment_main

    override val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavHost()
//        test()
    }

    private fun setupNavHost() {
        val navController = findNavController()

        // get fragment
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_main)!!

        // setup custom navigator
        val navigator = KeepStateNavigator(
            context = requireContext(),
            fragmentManager = navHostFragment.childFragmentManager,
            containerId = R.id.nav_host_fragment_main
        )
        navController.navigatorProvider += navigator

        // set navigation graph
        navController.setGraph(R.navigation.graph_main)

        bottom_nav?.setupWithNavController(navController)
    }

    fun test() {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, PermissionFragment.newInstance(), PermissionFragment.TAG)
            .commit()
    }
}