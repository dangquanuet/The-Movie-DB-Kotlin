package com.example.moviedb.ui.screen.permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentPermisisonBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.utils.*
import kotlinx.android.synthetic.main.fragment_permisison.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PermissionFragment : BaseFragment<FragmentPermisisonBinding, PermissionViewModel>() {

    companion object {
        const val TAG = "PermissionFragment"
        fun newInstance() = PermissionFragment()
    }

    override val layoutId: Int = R.layout.fragment_permisison

    override val viewModel: PermissionViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_1.setOnClickListener { case1() }
        button_2.setOnClickListener { requestSinglePermissionWithListener() }
        button_3.setOnClickListener { case3() }
        button_4.setOnClickListener { requestMultiplePermissionWithListener() }
    }

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
     * normal way to request runtime permission
     */
    private fun case1() {
        /*if (shouldAskPermission(Manifest.permission.READ_CONTACTS)) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_CONTACTS
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                DialogUtils.showMessage(
                    context = context,
                    message = "Please allow permission to use this feature",
                    textPositive = "OK",
                    positiveListener = {
                        requestPermissionReadContacts()
                    }
                )
            } else {
                if (isFirstTimeAskingPermission(Manifest.permission.READ_CONTACTS)) {
                    firstTimeAskingPermission(Manifest.permission.READ_CONTACTS, false)
                    // No explanation needed, we can request the permission.
                    requestPermissionReadContacts()

                    // REQUEST_READ_CONTACTS is an app-defined int constant.
                    // The callback method gets the result of the request.
                } else {
                    //Permission disabled by device policy or user denied permanently. Show proper error message
                    DialogUtils.showMessage(
                        context = context,
                        message = "Permission Disabled, Please allow permission to use this feature",
                        textPositive = "OK",
                        positiveListener = {
                            openAppDetailSettings()
                        }
                    )
                }
            }
        } else {
            // Permission has already been granted
        }*/
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
     * normal way to request multiple runtime permissions
     */
    private fun case3() {
        /*if (shouldAskPermissions(permissions)) {

            // Permissions is not granted
            // Should we show an explanation?
            if (shouldShowRequestPermissionsRationale(permissions)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request permissions.
                AlertDialog.Builder(this)
                    .setMessage("Please allow permissions to use this feature")
                    .setPositiveButton("OK") { dialog, which ->
                        ActivityCompat.requestPermissions(
                            this, permissions,
                            Constants.REQUEST_CAMERA_READ_CONTACTS
                        )
                    }
                    .setNegativeButton("Cancel") { dialog, which ->

                    }
                    .create().show()
            } else {
                if (isFirstTimeAskingPermissions(permissions)) {
                    firstTimeAskingPermissions(permissions, false)

                    // No explanation needed, we can request permissions.
                    ActivityCompat.requestPermissions(
                        this, permissions,
                        Constants.REQUEST_CAMERA_READ_CONTACTS
                    )

                    // REQUEST_CAMERA_READ_CONTACTS is an app-defined int constant.
                    // The callback method gets the result of the request.
                } else {
                    //Permissions disabled by device policy or user denied permanently. Show proper error message
                    AlertDialog.Builder(this)
                        .setMessage(
                            "Permissions Disabled, Please allow permissions to use this feature"
                        )
                        .setPositiveButton("OK") { dialog, which ->
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                        .setNegativeButton("Cancel") { dialog, which ->

                        }
                        .create().show()
                }
            }
        } else {
            // Permission has already been granted
        }*/
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

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}