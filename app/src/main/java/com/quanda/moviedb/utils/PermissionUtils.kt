package com.quanda.moviedb.utils

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.quanda.moviedb.data.local.PREFS_NAME


fun AppCompatActivity.firstTimeAskingPermission(permission: String, isFirstTime: Boolean) {
    val sharedPreference: SharedPreferences = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    sharedPreference.edit().putBoolean(permission, isFirstTime).apply()
}

fun AppCompatActivity.firstTimeAskingPermissions(permissions: Array<String>, isFirstTime: Boolean) {
    val sharedPreference: SharedPreferences = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    for (permission in permissions) {
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply()
    }
}


fun AppCompatActivity.isFirstTimeAskingPermission(permission: String): Boolean {
    return this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getBoolean(permission, true)
}

fun AppCompatActivity.isFirstTimeAskingPermissions(permissions: Array<String>): Boolean {
    val sharedPreference: SharedPreferences = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    for (permission in permissions) {
        if (sharedPreference.getBoolean(permission, true)) {
            return true
        }
    }
    return false
}

/*
* Check if version is marshmallow and above. deciding to ask runtime permission
* */
fun AppCompatActivity.shouldAskPermission(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

fun AppCompatActivity.shouldAskPermission(permission: String): Boolean {
    if (shouldAskPermission()) {
        if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            return true
        }
    }
    return false
}

/**
 * request single permission
 */
fun AppCompatActivity.requestPermission(permission: String, listener: PermissionAskListener) {
    /*
    * If permission is not granted
    * */
    if (shouldAskPermission(permission)) {
        /*
        * If permission denied previously
        * */
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            listener.onPermissionPreviouslyDenied()
        } else {
            /*
            * Permission denied or first time requested
            * */
            if (isFirstTimeAskingPermission(permission)) {
                firstTimeAskingPermission(permission, false)
                listener.onNeedPermission()
            } else {
                /*
                * Handle the feature without permission or ask user to manually allow permission
                * */
                listener.onPermissionDisabled()
            }
        }
    } else {
        listener.onPermissionGranted()
    }
}

fun AppCompatActivity.isPermissionsGranted(grantResults: IntArray): Boolean {
    for (grantResult in grantResults) {
        if (grantResult != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

fun AppCompatActivity.shouldAskPermissions(permissions: Array<String>): Boolean {
    if (shouldAskPermission()) {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }
    }
    return false
}

fun AppCompatActivity.shouldShowRequestPermissionsRationale(permissions: Array<String>): Boolean {
    for (permission in permissions) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            return true
        }
    }
    return false
}

/**
 * request multiple permissions
 */
fun AppCompatActivity.requestPermissions(permissions: Array<String>,
        listener: PermissionAskListener) {
    /*
    * If permissions is not granted
    * */
    if (shouldAskPermissions(permissions)) {
        /*
        * If permissions denied previously
        * */
        if (shouldShowRequestPermissionsRationale(permissions)) {
            listener.onPermissionPreviouslyDenied()
        } else {
            /*
            * Permission denied or first time requested
            * */
            if (isFirstTimeAskingPermissions(permissions)) {
                firstTimeAskingPermissions(permissions, false)
                listener.onNeedPermission()
            } else {
                /*
                * Handle the feature without permission or ask user to manually allow permission
                * */
                listener.onPermissionDisabled()
            }
        }
    } else {
        listener.onPermissionGranted()
    }
}

/*
* Callback on various cases on checking permission
*
* 1.  Below M, runtime permission not needed. In that case onPermissionGranted() would be called.
*     If permission is already granted, onPermissionGranted() would be called.
*
* 2.  Above M, if the permission is being asked first time onNeedPermission() would be called.
*
* 3.  Above M, if the permission is previously asked but not granted, onPermissionPreviouslyDenied()
*     would be called.
*
* 4.  Above M, if the permission is disabled by device policy or the user checked "Never ask again"
*     check box on previous request permission, onPermissionDisabled() would be called.
* */
interface PermissionAskListener {
    /*
    * Callback to ask permission
    * */
    fun onNeedPermission()

    /*
    * Callback on permission denied
    * */
    fun onPermissionPreviouslyDenied()

    /*
    * Callback on permission "Never show again" checked and denied
    * */
    fun onPermissionDisabled()

    /*
    * Callback on permission granted
    * */
    fun onPermissionGranted()
}
