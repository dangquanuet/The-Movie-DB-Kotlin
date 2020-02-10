package com.example.moviedb.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.example.moviedb.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context?.createLoadingDialog(
    cancelable: Boolean = false,
    canceledOnTouchOutside: Boolean = false
): AlertDialog? {
    val context = this ?: return null
    return MaterialAlertDialogBuilder(context)
        .setView(R.layout.layout_loading_dialog)
        .create().apply {
            setCancelable(cancelable)
            setCanceledOnTouchOutside(canceledOnTouchOutside)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
}

fun Context?.showLoadingDialog(
    cancelable: Boolean = false,
    canceledOnTouchOutside: Boolean = false
): AlertDialog? {
    this ?: return null
    val dialog: AlertDialog? = createLoadingDialog(
        cancelable = cancelable,
        canceledOnTouchOutside = canceledOnTouchOutside
    )
    dialog?.show()
    return dialog
}

fun Context?.showDialog(
    title: String? = null, message: String? = null,
    textPositive: String? = null, positiveListener: (() -> Unit)? = null,
    textNegative: String? = null, negativeListener: (() -> Unit)? = null,
    cancelable: Boolean = false, canceledOnTouchOutside: Boolean = true
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
        show()
    }
}

fun Context?.showDialog(
    title: Int? = null, message: Int? = null,
    textPositive: Int? = null, positiveListener: (() -> Unit)? = null,
    textNegative: Int? = null, negativeListener: (() -> Unit)? = null,
    cancelable: Boolean = false, canceledOnTouchOutside: Boolean = true
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
        show()
    }
}