package com.quanda.moviedb.ui

import android.os.Bundle
import com.quanda.moviedb.R
import com.quanda.moviedb.base.activity.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
