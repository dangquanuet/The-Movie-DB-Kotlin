package com.example.moviedb.ui.screen

import android.os.Bundle
import com.example.moviedb.R
import com.example.moviedb.ui.base.BaseActivity
import com.example.moviedb.ui.screen.main.MainFragment
import com.google.android.gms.ads.MobileAds
import org.koin.androidx.viewmodel.ext.viewModel

class MainActivity : BaseActivity() {

    val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this, getString(R.string.ADMOB_APP_ID))
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(), MainFragment.TAG)
                .commit()
        }
//        supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment? ?: return
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        when (currentFragment) {
            is MainFragment -> if (!currentFragment.onBack()) super.onBackPressed()
            else -> {
                if (currentFragment != null && currentFragment.childFragmentManager.backStackEntryCount >= 1) {
                    currentFragment.childFragmentManager.popBackStack()
                } else {
                    super.onBackPressed()
                }
            }
        }
    }
}
