package com.example.moviedb.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.moviedb.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * show single loading dialog
 */
var loadingDialog: Dialog? = null

fun Context?.showLoadingDialog(
    cancelable: Boolean = false,
    canceledOnTouchOutside: Boolean = false
): AlertDialog? {
    val context = this ?: return null
    return MaterialAlertDialogBuilder(context).apply {
        setView(R.layout.layout_loading_dialog)
    }.create().apply {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(canceledOnTouchOutside)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
        if (context is LifecycleOwner) {
            context.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    this@apply.dismiss()
                    if (loadingDialog === this@apply) {
                        loadingDialog = null
                    }
                }
            })
        }
        loadingDialog = this
        show()
    }
}

fun dismissLLoadingDialog() {
    if (loadingDialog?.isShowing == true) {
        loadingDialog?.dismiss()
    }
}

/**
 * show single alert dialog
 */
var showingDialog: Dialog? = null

fun Context?.showDialog(
    title: String? = null, message: String? = null,
    textPositive: String? = null, positiveListener: (() -> Unit)? = null,
    textNegative: String? = null, negativeListener: (() -> Unit)? = null,
    cancelable: Boolean = false, canceledOnTouchOutside: Boolean = false
): AlertDialog? {
    val context = this ?: return null
    return MaterialAlertDialogBuilder(context).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(textPositive) { dialog, which ->
            positiveListener?.invoke()
        }
        setNegativeButton(textNegative) { dialog, which ->
            negativeListener?.invoke()
        }
        setCancelable(cancelable)
    }.create().apply {
        setCanceledOnTouchOutside(canceledOnTouchOutside)
        if (showingDialog?.isShowing == true) {
            showingDialog?.dismiss()
        }
        if (context is LifecycleOwner) {
            context.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    this@apply.dismiss()
                    if (showingDialog === this@apply) {
                        showingDialog = null
                    }
                }
            })
        }
        setOnDismissListener {
            showingDialog = null
        }
        showingDialog = this
        show()
    }
}

fun Context?.showDialog(
    title: Int? = null, message: Int? = null,
    textPositive: Int? = null, positiveListener: (() -> Unit)? = null,
    textNegative: Int? = null, negativeListener: (() -> Unit)? = null,
    cancelable: Boolean = false, canceledOnTouchOutside: Boolean = false
): AlertDialog? {
    val context = this ?: return null
    return MaterialAlertDialogBuilder(context).apply {
        if (title != null) setTitle(title)
        if (message != null) setMessage(message)
        if (textPositive != null) {
            setPositiveButton(textPositive) { dialog, which ->
                positiveListener?.invoke()
            }
        }
        if (textNegative != null) {
            setNegativeButton(textNegative) { dialog, which ->
                negativeListener?.invoke()
            }
        }
        setCancelable(cancelable)
    }.create().apply {
        setCanceledOnTouchOutside(canceledOnTouchOutside)
        if (showingDialog?.isShowing == true) {
            showingDialog?.dismiss()
        }
        if (context is LifecycleOwner) {
            context.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    this@apply.dismiss()
                    if (showingDialog === this@apply) {
                        showingDialog = null
                    }
                }
            })
        }
        setOnDismissListener {
            showingDialog = null
        }
        showingDialog = this
        show()
    }
}

fun dismissShowingDialog() {
    if (showingDialog?.isShowing == true) {
        showingDialog?.dismiss()
    }
}
