package com.example.moviedb.ui.screen.permission

import android.Manifest
import android.os.Bundle
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
        button_2.setSingleClick { requestSinglePermissionWithListener() }
        button_4.setSingleClick { requestMultiplePermissionWithListener() }
    }

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
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
        )
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

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}