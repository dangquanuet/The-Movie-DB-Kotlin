package com.quanda.moviedb.utils

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ActivityUtils {



}

fun AppCompatActivity.goToActivity(activity: Class<*>, bundle: Bundle? = null, flag: Int = 0) {
    val intent = Intent(this, activity)
    if (flag != 0) {
        intent.flags = flag
    }
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    this.startActivity(intent)
}