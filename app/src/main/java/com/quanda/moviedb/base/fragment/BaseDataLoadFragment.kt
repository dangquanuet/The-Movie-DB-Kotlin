package com.quanda.moviedb.base.fragment

import android.app.Dialog
import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ViewDataBinding
import com.quanda.moviedb.base.viewmodel.BaseDataLoadViewModel
import com.quanda.moviedb.utils.DialogUtils

abstract class BaseDataLoadFragment<View : ViewDataBinding, ViewModel : BaseDataLoadViewModel> : BaseDataBindFragment<View, ViewModel>() {

    lateinit var loadingDialog: Dialog

    val loadingCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            handleLoadingChanged((sender as ObservableBoolean).get())
        }
    }

    abstract fun initViewModel(): ViewModel

    override fun initData() {
        if (context != null) {
            loadingDialog = DialogUtils.createLoadingDialog(context, false)
        }
        viewModel = initViewModel()
        viewModel.isDataLoading.addOnPropertyChangedCallback(loadingCallback)
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
        viewModel.isDataLoading.removeOnPropertyChangedCallback(loadingCallback)
    }

    fun showLoadingDialog() {
        if (activity == null || activity!!.isFinishing || !isAdded) return
        loadingDialog.show()
    }

    fun hideLoadingDialog() {
        if (activity == null || activity!!.isFinishing || !isAdded) return
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