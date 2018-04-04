package com.quanda.moviedb.base.activity

import android.app.Dialog
import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ViewDataBinding
import com.quanda.moviedb.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.utils.DialogUtils

abstract class BaseDataLoadActivity<View : ViewDataBinding, ViewModel : BaseDataLoadViewModel> : BaseDataBindActivity<View, ViewModel>() {

    lateinit var loadingDialog: Dialog

    val loadingCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            handleLoadingChanged((sender as ObservableBoolean).get())
        }
    }

    abstract fun initViewModel(): ViewModel

    override fun initData() {
        loadingDialog = DialogUtils.createLoadingDialog(this, false)
        viewModel = initViewModel()
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

    open fun handleLoadingChanged(isLoading: Boolean) {
        if (isLoading) {
            showLoadingDialog()
        } else {
            hideLoadingDialog()
        }
    }
}