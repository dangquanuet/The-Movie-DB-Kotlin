package com.quanda.moviedb.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.quanda.moviedb.R

class DialogUtils {

    companion object {
        fun createLoadingDialog(context: Context?, cancelable: Boolean): Dialog {
            val dialog = Dialog(context)
            dialog.setCancelable(cancelable)
            dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            dialog.setContentView(R.layout.layout_loading_dialog)
            return dialog
        }
    }
}