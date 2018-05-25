package com.quanda.moviedb.utils

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

// top level fun in kotlin = static fun in java
// use for utils class
// other option is extension function

fun AppCompatActivity.goToActivity(activity: Class<*>, bundle: Bundle? = null, flag: Int = 0) {
    startActivity(Intent(this, activity).apply {
        if (flag != 0) flags = flag
        if (bundle != null) putExtras(bundle)
    })
}

fun AppCompatActivity.goToActivityForResult(activity: Class<*>, bundle: Bundle? = null,
        flag: Int = 0, requestCode: Int) {
    startActivityForResult(Intent(this, activity).apply {
        if (flag != 0) flags = flag
        if (bundle != null) putExtras(bundle)
    }, requestCode)
}

fun AppCompatActivity.logError(message: String) {
    Log.e(this::class.java.simpleName, message);
}