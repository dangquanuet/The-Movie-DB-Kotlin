package com.example.moviedb.ui.screen.permission

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentPermisisonBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment : BaseFragment<FragmentPermisisonBinding, BaseViewModel>() {

    companion object {
        const val TAG = "PermissionFragment"
        fun newInstance() = PermissionFragment()
    }

    override val layoutId: Int = R.layout.fragment_permisison

    override val viewModel: BaseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.button1.setSingleClick { requestSinglePermissionWithListener() }
        viewBinding.button2.setSingleClick { requestMultiplePermissionWithListener() }
    }

    // single permission
    private val singlePermission = arrayOf(Manifest.permission.WRITE_CONTACTS)
    private val singlePermissionCode = 2001

    /**
     * request single permission with listener
     */
    private fun requestSinglePermissionWithListener() {
        requestPermissions(
            singlePermission,
            singlePermissionCode,
            object : RequestPermissionListener {
                override fun onPermissionRationaleShouldBeShown(requestPermission: () -> Unit) {
                    baseActivity.showDialog(
                        message = "Please allow permission to use this feature",
                        firstText = "OK",
                        firstListener = {
                            requestPermission.invoke()
                        },
                        secondText = "Cancel"
                    )
                }

                override fun onPermissionPermanentlyDenied(openAppSetting: () -> Unit) {
                    baseActivity.showDialog(
                        message = "Permission Disabled, Please allow permission to use this feature",
                        firstText = "OK",
                        firstListener = {
                            openAppSetting.invoke()
                        },
                        secondText = "Cancel"
                    )
                }

                override fun onPermissionGranted() {
                    showToast("Granted, do work")
                }
            })
    }

    // multiple permissions
    private val multiplePermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA
    )
    private val multiplePermissionsCode = 2111

    /**
     * request multiple permissions with listener
     */
    private fun requestMultiplePermissionWithListener() {
        requestPermissions(
            multiplePermissions,
            multiplePermissionsCode,
            object : RequestPermissionListener {
                override fun onPermissionRationaleShouldBeShown(requestPermission: () -> Unit) {
                    baseActivity.showDialog(
                        message = "Please allow permissions to use this feature",
                        firstText = "OK",
                        firstListener = {
                            requestPermission.invoke()
                        },
                        secondText = "Cancel"
                    )
                }

                override fun onPermissionPermanentlyDenied(openAppSetting: () -> Unit) {
                    baseActivity.showDialog(
                        message = "Permission Disabled, Please allow permissions to use this feature",
                        firstText = "OK",
                        firstListener = {
                            openAppSetting.invoke()
                        },
                        secondText = "Cancel"
                    )
                }

                override fun onPermissionGranted() {
                    showToast("Granted, do work")
                }
            })
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // single permission
        handleOnRequestPermissionResult(
            singlePermissionCode,
            requestCode,
            permissions,
            grantResults,
            object : PermissionResultListener {
                override fun onPermissionRationaleShouldBeShown() {
                    showToast("permission denied")
                }

                override fun onPermissionPermanentlyDenied() {
                    showToast("permission permanently disabled")
                }

                override fun onPermissionGranted() {
                    showToast("permission granted")
                }
            })

        // multiple permission
        handleOnRequestPermissionResult(
            multiplePermissionsCode,
            requestCode,
            permissions,
            grantResults,
            object : PermissionResultListener {
                override fun onPermissionRationaleShouldBeShown() {
                    showToast("permission denied")
                }

                override fun onPermissionPermanentlyDenied() {
                    showToast("permission permanently disabled")
                }

                override fun onPermissionGranted() {
                    showToast("permission granted")
                }
            }
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(context, "Fragment $message", Toast.LENGTH_SHORT).show()
    }

}
