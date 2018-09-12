package com.quanda.moviedb.ui.screen

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.quanda.moviedb.R
import com.quanda.moviedb.ui.base.BaseActivity
import com.quanda.moviedb.ui.screen.main.MainFragment
import javax.inject.Inject

/**
 * androidx.activity is not extend from appcompat activity
 */
class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: MainActivityViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .replace(R.id.parent, MainFragment.newInstance(), MainFragment.TAG)
                .commit()
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.parent)
        when (currentFragment) {
            is MainFragment -> if (!currentFragment.onBack()) super.onBackPressed()
            else -> super.onBackPressed()
        }
    }
}
