package com.quanda.moviedb.ui.base.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AlertDialog
import com.quanda.moviedb.ui.base.viewmodel.BaseViewModel
import com.quanda.moviedb.utils.DialogUtils
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<View : ViewDataBinding, ViewModel : BaseViewModel> : DaggerAppCompatActivity() {

    lateinit var binding: View

    abstract val viewModel: ViewModel

    @get:LayoutRes
    abstract val layoutId: Int

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.setLifecycleOwner(this)

        loadingDialog = DialogUtils.createLoadingDialog(this, false)
        viewModel.isDataLoading.observe(this, Observer {
            handleLoadingChanged(it == true)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.isDataLoading.removeObservers(this)
        viewModel.onActivityDestroyed()
    }

    var loadingDialog: AlertDialog? = null

    fun showLoadingDialog() {
        if (isFinishing) return
        loadingDialog?.show()
    }

    fun hideLoadingDialog() {
        if (isFinishing) return
        loadingDialog?.dismiss()
    }

    open fun handleLoadingChanged(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else hideLoadingDialog()
    }
}