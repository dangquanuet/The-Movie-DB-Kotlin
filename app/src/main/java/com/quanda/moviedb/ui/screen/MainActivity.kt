package com.quanda.moviedb.ui.screen

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.quanda.moviedb.R
import com.quanda.moviedb.ui.base.BaseActivity
import com.quanda.moviedb.ui.screen.main.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance(), MainFragment.TAG)
//                .commit()
//        }
        supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment? ?: return
    }

//    override fun onBackPressed() {
//        val currentFragment = supportFragmentManager.findFragmentById(R.id.parent)
//        when (currentFragment) {
//            is MainFragment -> if (!currentFragment.onBack()) super.onBackPressed()
//            else -> {
//                if (currentFragment != null && currentFragment.childFragmentManager.backStackEntryCount >= 1) {
//                    currentFragment.childFragmentManager.popBackStack()
//                } else {
//                    super.onBackPressed()
//                }
//            }
//        }
//    }
}
