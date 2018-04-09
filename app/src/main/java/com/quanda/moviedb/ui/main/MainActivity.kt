package com.quanda.moviedb.ui.main

import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.quanda.moviedb.R
import com.quanda.moviedb.base.activity.BaseDataLoadActivity
import com.quanda.moviedb.databinding.ActivityMainBinding

class MainActivity : BaseDataLoadActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {

    companion object {
        const val FRAGMENT_TAG = "fragment_tag_"
    }

    lateinit var bottomNavigation: AHBottomNavigation
    var currentPositionFragment = Tab.POPULAR.position

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): MainViewModel {
        return MainViewModel(this, this)
    }

    override fun initData() {
        super.initData()
        binding.viewModel = viewModel

        initBottomNavigation()
    }

    fun initBottomNavigation() {
        bottomNavigation = binding.bottomNavigation
        val bottomNavigationAdapter = AHBottomNavigationAdapter(this,
                R.menu.menu_bottom_navigation)
        bottomNavigationAdapter.setupWithBottomNavigation(bottomNavigation)

        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomNavigation.defaultBackgroundColor = ContextCompat.getColor(this, R.color.white)

        bottomNavigation.setOnTabSelectedListener(
                AHBottomNavigation.OnTabSelectedListener { position, wasSelected ->
                    onClickBottomNavigationItem(position)
                })
        bottomNavigation.currentItem = Tab.POPULAR.position
    }

    fun onClickBottomNavigationItem(position: Int): Boolean {
        val currentTag = getTabFragmentTag(currentPositionFragment)
        val newTag = getTabFragmentTag(position)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val currentFragment = fragmentManager.findFragmentByTag(currentTag)
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }

        var newFragment: Fragment? = fragmentManager.findFragmentByTag(newTag)
        if (newFragment == null) {
            newFragment = newFragmentInstance(position)
            if (newFragment.isAdded()) {
                fragmentTransaction.show(newFragment)
            } else {
                fragmentTransaction.add(R.id.frame_layout, newFragment, newTag)
            }
        } else {
            fragmentTransaction.show(newFragment)
        }
        currentPositionFragment = position
        fragmentTransaction.commit()

        return true
    }

    fun getTabFragmentTag(position: Int) = FRAGMENT_TAG + position

    fun newFragmentInstance(position: Int): Fragment {
        return when (position) {
            Tab.POPULAR.position -> Fragment()
            Tab.TOP_RATED.position -> Fragment()
            Tab.FAVORITE.position -> Fragment()
            Tab.PROFILE.position -> Fragment()
            else -> Fragment()
        }
    }

    enum class Tab(val position: Int) {
        POPULAR(0), TOP_RATED(1), FAVORITE(2), PROFILE(3)
    }
}
