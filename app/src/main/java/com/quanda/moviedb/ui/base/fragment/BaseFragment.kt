package com.quanda.moviedb.ui.base.fragment

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quanda.moviedb.ui.base.viewmodel.BaseViewModel
import com.quanda.moviedb.utils.DialogUtils

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {

    lateinit var binding: ViewBinding

    abstract val viewModel: ViewModel

    @get:LayoutRes
    abstract val layoutId: Int

    var loadingDialog: AlertDialog? = null

    abstract fun initViewModel(): ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.apply {
            root.isClickable = true
            setLifecycleOwner(this@BaseFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingDialog = DialogUtils.createLoadingDialog(context, false)
        viewModel.isDataLoading.observe(this, Observer {
            handleLoadingChanged(it == true)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.isDataLoading.removeObservers(this)
        viewModel.onActivityDestroyed()
    }

    fun showLoadingDialog() {
        if (activity == null || activity!!.isFinishing || !isAdded) return
        loadingDialog?.show()
    }

    fun hideLoadingDialog() {
        if (activity == null || activity!!.isFinishing || !isAdded) return
        loadingDialog?.dismiss()
    }

    open fun handleLoadingChanged(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else hideLoadingDialog()
    }
}