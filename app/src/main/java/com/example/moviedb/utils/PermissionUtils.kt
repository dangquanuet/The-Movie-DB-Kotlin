package com.example.moviedb.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
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
     * request single permission with listener
     */
    private fun requestSinglePermissionWithListener() {
        requestPermission(singlePermission, singlePermissionCode, object : PermissionAskListener {
            override fun onPermissionRationaleShouldBeShown(requestPermission: () -> Unit) {
                DialogUtils.showMessage(
                    context = context,
                    message = "Please allow permission to use this feature",
                    textPositive = "OK",
                    positiveListener = {
                        requestPermission.invoke()
                    },
                    textNegative = "Cancel"
                )
            }

            override fun onPermissionPermanentlyDenied(openAppSetting: () -> Unit) {
                DialogUtils.showMessage(
                    context = context,
                    message = "Permission Disabled, Please allow permission to use this feature",
                    textPositive = "OK",
                    positiveListener = {
                        openAppSetting.invoke()
                    },
                    textNegative = "Cancel"
                )
            }

            override fun onPermissionGranted() {
                showToast("Granted, do work")
            }
        })
    }

    // multiple permissions
    private val multiplePermissions =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
    private val multiplePermissionsCode = 1111

    /**
     * request multiple permissions with listener
     */
    private fun requestMultiplePermissionWithListener() {
        requestPermissions(
            multiplePermissions,
            multiplePermissionsCode,
            object : PermissionAskListener {
                override fun onPermissionRationaleShouldBeShown(requestPermission: () -> Unit) {
                    DialogUtils.showMessage(
                        context = context,
                        message = "Please allow permissions to use this feature",
                        textPositive = "OK",
                        positiveListener = {
                            requestPermission.invoke()
                        },
                        textNegative = "Cancel"
                    )
                }

                override fun onPermissionPermanentlyDenied(openAppSetting: () -> Unit) {
                    DialogUtils.showMessage(
                        context = context,
                        message = "Permission Disabled, Please allow permissions to use this feature",
                        textPositive = "OK",
                        positiveListener = {
                            openAppSetting.invoke()
                        },
                        textNegative = "Cancel"
                    )
                }

                override fun onPermissionGranted() {
                    showToast("Granted, do work")
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
                if (isGrantedGrantResults(grantResults)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    showToast("permission granted")
                } else {
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
                    if (isGrantedGrantResults(grantResults)) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
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
        }
    }
}
*/

/**
 * open app details setting
 */
fun Context.openAppDetailSettings() {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

/**
 * set first time asking single permission
 */
fun Context.firstTimeAskingPermission(permission: String, isFirstTime: Boolean) {
    val sharedPreference: SharedPreferences? = getSharedPreferences(packageName, MODE_PRIVATE)
    sharedPreference?.edit()?.putBoolean(permission, isFirstTime)?.apply()
}

/**
 * set first time asking multiple permissions
 */
fun Context.firstTimeAskingPermissions(permissions: Array<String>, isFirstTime: Boolean) {
    val sharedPreference: SharedPreferences? = getSharedPreferences(packageName, MODE_PRIVATE)
    for (permission in permissions) {
        sharedPreference?.edit()?.putBoolean(permission, isFirstTime)?.apply()
    }
}

/**
 * check if first time asking single permission
 */
fun Context.isFirstTimeAskingPermission(permission: String): Boolean {
    return getSharedPreferences(packageName, MODE_PRIVATE)?.getBoolean(permission, true) ?: false
}

/**
 * check if first time asking multiple permissions
 */
fun Context.isFirstTimeAskingPermissions(permissions: Array<String>): Boolean {
    val sharedPreference: SharedPreferences? = getSharedPreferences(packageName, MODE_PRIVATE)
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
fun isGrantedGrantResults(grantResults: IntArray): Boolean {
    if (grantResults.isEmpty()) return false
    for (grantResult in grantResults) {
        if (grantResult != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

/**
 * check if single permission is granted or not
 */
fun Context.shouldAskPermission(permission: String): Boolean {
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
 * check if multiple permissions are granted or not
 */
fun Context.shouldAskPermissions(permissions: Array<String>): Boolean {
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
 * check if should show request permissions rationale in activity
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
 * check if should show request permissions rationale in fragment
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
 * request single permission in activity
 */
@TargetApi(Build.VERSION_CODES.M)
fun <T : Activity> T.requestPermission(
    permission: String,
    permissionRequestCode: Int,
    listener: PermissionAskListener
) {
    // permission is not granted
    if (shouldAskPermission(permission)) {
        // permission denied previously
        if (shouldShowRequestPermissionRationale(permission)) {
            listener.onPermissionRationaleShouldBeShown {
                requestPermissions(arrayOf(permission), permissionRequestCode)
            }
        } else {
            // permission denied or first time requested
            if (isFirstTimeAskingPermission(permission)) {
                firstTimeAskingPermission(permission, false)
                // request permission
                requestPermissions(arrayOf(permission), permissionRequestCode)
            } else {
                // permission disabled
                // handle the feature without permission or ask user to manually allow permission
                listener.onPermissionPermanentlyDenied {
                    openAppDetailSettings()
                }
            }
        }
    } else {
        // permission granted
        listener.onPermissionGranted()
    }
}

/**
 * request single permission in fragment
 */
fun <T : Fragment> T.requestPermission(
    permission: String,
    permissionRequestCode: Int,
    listener: PermissionAskListener
) {
    val context = context ?: return

    // permission is not granted
    if (context.shouldAskPermission(permission)) {
        // permission denied previously
        if (shouldShowRequestPermissionRationale(permission)) {
            listener.onPermissionRationaleShouldBeShown {
                requestPermissions(arrayOf(permission), permissionRequestCode)
            }
        } else {
            // permission denied or first time requested
            if (context.isFirstTimeAskingPermission(permission)) {
                context.firstTimeAskingPermission(permission, false)
                // request permission
                requestPermissions(arrayOf(permission), permissionRequestCode)
            } else {
                // permission disabled
                // handle the feature without permission or ask user to manually allow permission
                listener.onPermissionPermanentlyDenied {
                    context.openAppDetailSettings()
                }
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
@TargetApi(Build.VERSION_CODES.M)
fun <T : Activity> T.requestPermissions(
    permissions: Array<String>,
    permissionRequestCode: Int,
    listener: PermissionAskListener
) {
    // permissions is not granted
    if (shouldAskPermissions(permissions)) {
        // permissions denied previously
        if (shouldShowRequestPermissionsRationale(permissions)) {
            listener.onPermissionRationaleShouldBeShown {
                requestPermissions(permissions, permissionRequestCode)
            }
        } else {
            // Permission denied or first time requested
            if (isFirstTimeAskingPermissions(permissions)) {
                firstTimeAskingPermissions(permissions, false)
                // request permissions
                requestPermissions(permissions, permissionRequestCode)
            } else {
                // permission disabled
                // Handle the feature without permission or ask user to manually allow permission
                listener.onPermissionPermanentlyDenied {
                    openAppDetailSettings()
                }
            }
        }
    } else {
        // permission granted
        listener.onPermissionGranted()
    }
}

/**
 * request multiple permissions in fragment
 */
fun <T : Fragment> T.requestPermissions(
    permissions: Array<String>,
    permissionRequestCode: Int,
    listener: PermissionAskListener
) {
    val context = context ?: return

    // permissions is not granted
    if (context.shouldAskPermissions(permissions)) {
        // permissions denied previously
        if (shouldShowRequestPermissionsRationale(permissions)) {
            listener.onPermissionRationaleShouldBeShown {
                requestPermissions(permissions, permissionRequestCode)
            }
        } else {
            // Permission denied or first time requested
            if (context.isFirstTimeAskingPermissions(permissions)) {
                context.firstTimeAskingPermissions(permissions, false)
                // request permissions
                requestPermissions(permissions, permissionRequestCode)
            } else {
                // permission disabled
                // Handle the feature without permission or ask user to manually allow permission
                listener.onPermissionPermanentlyDenied {
                    context.openAppDetailSettings()
                }
            }
        }
    } else {
        // permission granted
        listener.onPermissionGranted()
    }
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
     * Callback on permission previously denied
     * should show permission rationale and continue permission request
     */
    fun onPermissionRationaleShouldBeShown(requestPermission: () -> Unit)

    /**
     * Callback on permission "Never show again" checked and denied
     * should show message and open app setting
     */
    fun onPermissionPermanentlyDenied(openAppSetting: () -> Unit)

    /**
     * Callback on permission granted
     */
    fun onPermissionGranted()
}
