package com.quanda.moviedb.base.activity

import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ViewDataBinding
import com.quanda.moviedb.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.utils.DialogUtils

abstract class BaseDataLoadActivity<T : ViewDataBinding, K : BaseDataLoadViewModel> : BaseDataBindActivity<T, K>() {

    val loadingDialog = DialogUtils.createLoadingDialog(this, false)

    val loadingCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            handleLoadingChanged((sender as ObservableBoolean).get())
        }
    }

    abstract fun initViewModel(): K

    override fun initData() {
        initViewModel()
        viewModel.isDataLoading.addOnPropertyChangedCallback(loadingCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.isDataLoading.removeOnPropertyChangedCallback(loadingCallback)
    }

    fun showLoadingDialog() {
        if (isFinishing) return
        loadingDialog.show()
    }

    fun hideLoadingDialog() {
        if (isFinishing) return
        loadingDialog.dismiss()
    }

    fun handleLoadingChanged(isLoading: Boolean) {
        if (isLoading) {
            showLoadingDialog()
        } else {
            hideLoadingDialog()
        }
    }
}