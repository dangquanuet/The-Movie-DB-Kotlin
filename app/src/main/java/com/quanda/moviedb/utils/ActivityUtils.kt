package com.quanda.moviedb.utils

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.goToActivity(activity: Class<*>, bundle: Bundle? = null, flag: Int = 0) {
    startActivity(Intent(this, activity).apply {
        if (flag != 0) flags = flag
        if (bundle != null) putExtras(bundle)
    })
}