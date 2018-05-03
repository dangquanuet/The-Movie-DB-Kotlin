package com.quanda.moviedb.ui.base.fragment

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.databinding.ViewDataBinding
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.utils.DialogUtils

abstract class BaseDataLoadFragment<View : ViewDataBinding, ViewModel : BaseDataLoadViewModel> : BaseDataBindFragment<View, ViewModel>() {

    lateinit var loadingDialog: Dialog

    abstract fun initViewModel(): ViewModel

    override fun initData() {
        if (context != null) {
            loadingDialog = DialogUtils.createLoadingDialog(context, false)
        }
        viewModel = initViewModel()
        viewModel.isDataLoading.observe(this,
                Observer<Boolean> { t -> handleLoadingChanged(t == true) })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.isDataLoading.removeObservers(this)
    }

    fun showLoadingDialog() {
        if (activity == null || activity!!.isFinishing || !isAdded) return
        loadingDialog.show()
    }

    fun hideLoadingDialog() {
        if (activity == null || activity!!.isFinishing || !isAdded) return
        loadingDialog.dismiss()
    }

    open fun handleLoadingChanged(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else hideLoadingDialog()
    }
}