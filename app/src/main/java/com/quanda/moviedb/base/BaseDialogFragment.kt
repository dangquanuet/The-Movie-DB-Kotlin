package com.quanda.moviedb.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseDialogFragment<T : ViewDataBinding> : DialogFragment() {

    lateinit var binding: T

    abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog.apply {
            setCancelable(false)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}