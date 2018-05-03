package com.quanda.moviedb.ui.base.activity

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.databinding.ViewDataBinding
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.utils.DialogUtils

abstract class BaseDataLoadActivity<View : ViewDataBinding, ViewModel : BaseDataLoadViewModel> : BaseDataBindActivity<View, ViewModel>() {

    lateinit var loadingDialog: Dialog

    abstract fun initViewModel(): ViewModel

    override fun initData() {
        loadingDialog = DialogUtils.createLoadingDialog(this, false)
        viewModel = initViewModel()
        viewModel.isDataLoading.observe(this,
                Observer<Boolean> { t -> handleLoadingChanged(t == true) })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.isDataLoading.removeObservers(this)
    }

    fun showLoadingDialog() {
        if (isFinishing) return
        loadingDialog.show()
    }

    fun hideLoadingDialog() {
        if (isFinishing) return
        loadingDialog.dismiss()
    }

    open fun handleLoadingChanged(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else hideLoadingDialog()
    }
}