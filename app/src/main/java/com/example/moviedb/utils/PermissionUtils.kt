package com.example.moviedb.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


/**
 * example request runtime permission in fragment
 */
/*
class Fragment {

    // single permission
    private val singlePermission = Manifest.permission.WRITE_CONTACTS
    private val singlePermissionCode = 1001

    /**
     * request single permission without listener
     */
    private fun requestSinglePermission() {
        requestPermissions(arrayOf(singlePermission), singlePermissionCode)
    }

    // multiple permissions
    private val multiplePermissions =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
    private val multiplePermissionsCode = 1111

    /**
     * request multiple permissions without listener
     */
    private fun requestMultiplePermissions() {
        requestPermissions(multiplePermissions, multiplePermissionsCode)
    }

    /**
     * open app details setting
     */
    private fun openAppDetailSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    /**
     * simple way to request runtime permission with utils
     */
    private fun requestSinglePermissionWithListener() {
        requestPermission(singlePermission, object : PermissionAskListener {
            override fun onNeedPermission() {
                requestSinglePermission()
            }

            override fun onPermissionPreviouslyDenied() {
                DialogUtils.showMessage(
                    context = context,
                    message = "Please allow permission to use this feature",
                    textPositive = "OK",
                    positiveListener = {
                        requestSinglePermission()
                    },
                    textNegative = "Cancel"
                )
            }

            override fun onPermissionDisabled() {
                DialogUtils.showMessage(
                    context = context,
                    message = "Permission Disabled, Please allow permission to use this feature",
                    textPositive = "OK",
                    positiveListener = {
                        openAppDetailSettings()
                    },
                    textNegative = "Cancel"
                )
            }

            override fun onPermissionGranted() {
                //   readContacts()
                showToast("Granted")
            }
        })
    }

    /**
     * simple way to request multiple runtime permissions with utils
     */
    private fun requestMultiplePermissionWithListener() {
        requestPermissions(multiplePermissions, object : PermissionAskListener {
            override fun onNeedPermission() {
                requestMultiplePermissions()
            }

            override fun onPermissionPreviouslyDenied() {
                DialogUtils.showMessage(
                    context = context,
                    message = "Please allow permissions to use this feature",
                    textPositive = "OK",
                    positiveListener = {
                        requestMultiplePermissions()
                    },
                    textNegative = "Cancel"
                )
            }

            override fun onPermissionDisabled() {
                DialogUtils.showMessage(
                    context = context,
                    message = "Permission Disabled, Please allow permissions to use this feature",
                    textPositive = "OK",
                    positiveListener = {
                        openAppDetailSettings()
                    },
                    textNegative = "Cancel"
                )
            }

            override fun onPermissionGranted() {
                // permissions granted
                showToast("Granted")
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            // single permission
            singlePermissionCode -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.getOrNull(0) == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    showToast("permission granted")
                } else if (grantResults.getOrNull(0) == PackageManager.PERMISSION_DENIED) {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    if (shouldShowRequestPermissionRationale(singlePermission)) {
                        // permission denied
                        showToast("permission denied")
                    } else {
                        // permission disabled or never ask again
                        showToast("permission disabled")
                    }
                }
            }

            // multiple permission
            multiplePermissionsCode -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size == multiplePermissions.size) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (isPermissionsGranted(grantResults)) {
                        showToast("permissions granted")
                    } else {
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                        if (shouldShowRequestPermissionsRationale(multiplePermissions)) {
                            // permission denied
                            showToast("permission denied")
                        } else {
                            // permission disabled or never ask again
                            showToast("permission disabled")
                        }
                    }
                }
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
*/


/**
 * set first time asking permission
 */
fun Context?.firstTimeAskingPermission(permission: String, isFirstTime: Boolean) {
    val sharedPreference: SharedPreferences? = this?.getSharedPreferences(packageName, MODE_PRIVATE)
    sharedPreference?.edit()?.putBoolean(permission, isFirstTime)?.apply()
}

/**
 * set first time asking permissions
 */
fun Context?.firstTimeAskingPermissions(permissions: Array<String>, isFirstTime: Boolean) {
    val sharedPreference: SharedPreferences? = this?.getSharedPreferences(packageName, MODE_PRIVATE)
    for (permission in permissions) {
        sharedPreference?.edit()?.putBoolean(permission, isFirstTime)?.apply()
    }
}

/**
 * check is first time asking permission
 */
fun Context?.isFirstTimeAskingPermission(permission: String): Boolean {
    return this?.getSharedPreferences(packageName, MODE_PRIVATE)?.getBoolean(permission, true)
        ?: false
}

/**
 * check is first time asking permissions
 */
fun Context?.isFirstTimeAskingPermissions(permissions: Array<String>): Boolean {
    val sharedPreference: SharedPreferences? = this?.getSharedPreferences(packageName, MODE_PRIVATE)
    for (permission in permissions) {
        if (sharedPreference?.getBoolean(permission, true) == true) {
            return true
        }
    }
    return false
}

/**
 * Check if version is marshmallow and above. deciding to request runtime permission
 */
fun shouldRequestRuntimePermission(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

/**
 * check grandResults are granted
 */
fun isPermissionsGranted(grantResults: IntArray): Boolean {
    if (grantResults.isEmpty()) return false
    for (grantResult in grantResults) {
        if (grantResult != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

/**
 * check permission is granted or not
 */
fun Context?.shouldAskPermission(permission: String): Boolean {
    if (this == null) return false
    if (shouldRequestRuntimePermission()) {
        if (ContextCompat.checkSelfPermission(this, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
    }
    return false
}

/**
 * check permissions is granted or not
 */
fun Context?.shouldAskPermissions(permissions: Array<String>): Boolean {
    if (this == null) return false
    if (shouldRequestRuntimePermission()) {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return true
            }
        }
    }
    return false
}

/**
 * check should show request permissions rationale
 */
@TargetApi(Build.VERSION_CODES.M)
fun <T : Activity> T.shouldShowRequestPermissionsRationale(permissions: Array<String>): Boolean {
    for (permission in permissions) {
        if (shouldShowRequestPermissionRationale(permission)) {
            return true
        }
    }
    return false
}

/**
 * check should show request permissions rationale
 */
fun <T : Fragment> T.shouldShowRequestPermissionsRationale(permissions: Array<String>): Boolean {
    for (permission in permissions) {
        if (shouldShowRequestPermissionRationale(permission)) {
            return true
        }
    }
    return false
}

/**
 * request single permission
 */
@TargetApi(Build.VERSION_CODES.M)
fun <T : Activity> T.requestPermission(permission: String, listener: PermissionAskListener) {
    // permission is not granted
    if (shouldAskPermission(permission)) {
        // permission denied previously
        if (shouldShowRequestPermissionRationale(permission)) {
            listener.onPermissionPreviouslyDenied()
        } else {
            // permission denied or first time requested
            if (isFirstTimeAskingPermission(permission)) {
                firstTimeAskingPermission(permission, false)
                listener.onNeedPermission()
            } else {
                // permission disabled
                // handle the feature without permission or ask user to manually allow permission
                listener.onPermissionDisabled()
            }
        }
    } else {
        // permission granted
        listener.onPermissionGranted()
    }
}

/**
 * request single permission
 */
fun <T : Fragment> T.requestPermission(permission: String, listener: PermissionAskListener) {
    activity?.requestPermission(permission, listener)
}

/**
 * request multiple permissions in activity
 */
fun <T : Activity> T.requestPermissions(
    permissions: Array<String>,
    listener: PermissionAskListener
) {
    // permissions is not granted
    if (shouldAskPermissions(permissions)) {
        // permissions denied previously
        if (shouldShowRequestPermissionsRationale(permissions)) {
            listener.onPermissionPreviouslyDenied()
        } else {
            // Permission denied or first time requested
            if (isFirstTimeAskingPermissions(permissions)) {
                firstTimeAskingPermissions(permissions, false)
                listener.onNeedPermission()
            } else {
                // permission disabled
                // Handle the feature without permission or ask user to manually allow permission
                listener.onPermissionDisabled()
            }
        }
    } else {
        // permission granted
        listener.onPermissionGranted()
    }
}

/**
 * request multiple permissions in activity
 */
fun <T : Fragment> T.requestPermissions(
    permissions: Array<String>,
    listener: PermissionAskListener
) {
    activity?.requestPermissions(permissions, listener)
}

/**
 * Callback on various cases on checking permission
 *
 * 1.  Below M, runtime permission not needed. In that case onPermissionGranted() would be called.
 * If permission is already granted, onPermissionGranted() would be called.
 *
 * 2.  Above M, if the permission is being asked first time onNeedPermission() would be called.
 *
 * 3.  Above M, if the permission is previously asked but not granted, onPermissionPreviouslyDenied()
 * would be called.
 *
 * 4.  Above M, if the permission is disabled by device policy or the user checked "Never ask again"
 * check box on previous request permission, onPermissionDisabled() would be called.
 */
interface PermissionAskListener {
    /**
     * Callback to ask permission
     */
    fun onNeedPermission()

    /**
     * Callback on permission denied
     */
    fun onPermissionPreviouslyDenied()

    /**
     * Callback on permission "Never show again" checked and denied
     */
    fun onPermissionDisabled()

    /**
     * Callback on permission granted
     */
    fun onPermissionGranted()
}
