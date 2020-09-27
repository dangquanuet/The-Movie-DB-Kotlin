package com.example.moviedb.ui.screen.oldmain

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentMainBinding
import com.example.moviedb.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_old_main.*

@AndroidEntryPoint
class OldMainFragment : BaseFragment<FragmentMainBinding, OldMainViewModel>() {

    companion object {
        private const val FRAGMENT_TAG = "FRAGMENT_TAG"
    }

    override val layoutId: Int = R.layout.fragment_old_main

    override val viewModel: OldMainViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        layout_tab_popular.setOnClickListener {
            onClickBottomNavigationItem(Tab.POPULAR.position)
        }
        layout_tab_rated.setOnClickListener {
            onClickBottomNavigationItem(Tab.TOP_RATED.position)
        }
        layout_tab_favorite.setOnClickListener {
            onClickBottomNavigationItem(Tab.FAVORITE.position)
        }
        layout_tab_profile.setOnClickListener {
            onClickBottomNavigationItem(Tab.PROFILE.position)
        }
        onClickBottomNavigationItem(Tab.POPULAR.position)
    }

    private fun onClickBottomNavigationItem(position: Int): Boolean {
        val currentTag = getTabFragmentTag(viewModel.currentTab.value ?: Tab.POPULAR.position)
        val newTag = getTabFragmentTag(position)

        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val currentFragment = fragmentManager.findFragmentByTag(currentTag)
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }

        var newFragment: Fragment? = fragmentManager.findFragmentByTag(newTag)
        if (newFragment == null) {
            newFragment = newFragmentInstance(position)
            if (newFragment.isAdded) {
                fragmentTransaction.show(newFragment)
            } else {
                fragmentTransaction.add(R.id.frame_layout, newFragment, newTag)
            }
        } else {
            fragmentTransaction.show(newFragment)
        }

        viewModel.currentTab.value = position
        fragmentTransaction.commit()

        return true
    }

    private fun getTabFragmentTag(position: Int) = FRAGMENT_TAG + position

    private fun newFragmentInstance(position: Int): Fragment {
        return when (position) {
            Tab.POPULAR.position -> Fragment()/*PopularMovieFragment.newInstance(
                MovieListType.POPULAR.type
            )*/
            Tab.TOP_RATED.position -> Fragment()/*PopularMovieFragment.newInstance(
                MovieListType.TOP_RATED.type
            )*/
            Tab.FAVORITE.position -> Fragment()
            Tab.PROFILE.position -> Fragment()
            else -> Fragment()
        }
    }

}