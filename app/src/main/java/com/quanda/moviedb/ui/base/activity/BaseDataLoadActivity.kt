package com.quanda.moviedb.ui.base.activity

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.PersistableBundle
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.utils.DialogUtils

abstract class BaseDataLoadActivity<View : ViewDataBinding, ViewModel : BaseDataLoadViewModel> : BaseActivity<View, ViewModel>() {

    lateinit var loadingDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        loadingDialog = DialogUtils.createLoadingDialog(this, false)
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