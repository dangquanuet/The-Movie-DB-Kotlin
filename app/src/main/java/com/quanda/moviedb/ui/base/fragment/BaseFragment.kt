package com.quanda.moviedb.ui.base.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quanda.moviedb.R
import com.quanda.moviedb.ui.base.navigator.BaseNavigator
import com.quanda.moviedb.ui.base.viewmodel.BaseViewModel
import com.quanda.moviedb.utils.DialogUtils
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment(), BaseNavigator {

    abstract val bindingVariable: Int

    lateinit var viewBinding: ViewBinding

    abstract val viewModel: ViewModel

    @get:LayoutRes
    abstract val layoutId: Int

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

//    var loadingDialog: AlertDialog? = null

    private var mAlertDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            setVariable(bindingVariable, viewModel)
            root.isClickable = true
            setLifecycleOwner(this@BaseFragment)
            executePendingBindings()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAlertDialog = DialogUtils.createLoadingDialog(context, false)
//        viewModel.isLoading.observe(this, Observer {
//            handleLoadingChanged(it == true)
//        })

        viewModel.apply {
            isLoading.observe(this@BaseFragment, Observer {
                handleShowLoading(it == true)
            })
            errorMessage.observe(this@BaseFragment, Observer {
                hideLoading()
                if (it != null && it.isNotBlank()) {
                    handleShowErrorMessage(it)
                }
            })
        }
    }

    open fun handleShowLoading(isLoading: Boolean) {
        if (isLoading) showLoading() else hideLoading()
    }

    fun handleShowErrorMessage(message: String) {
        DialogUtils.showMessage(context, message = message, textPositive = getString(R.string.ok))
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.isLoading.removeObservers(this)
        viewModel.errorMessage.removeObservers(this)
        viewModel.onActivityDestroyed()
    }

    fun showLoading() {
        hideLoading()
        mAlertDialog?.show()
    }

    fun hideLoading() {
        if (mAlertDialog != null && mAlertDialog!!.isShowing) {
            mAlertDialog?.cancel()
        }
    }

//    fun showLoadingDialog() {
//        if (activity == null || activity!!.isFinishing || !isAdded) return
//        loadingDialog?.show()
//    }
//
//    fun hideLoadingDialog() {
//        if (activity == null || activity!!.isFinishing || !isAdded) return
//        loadingDialog?.dismiss()
//    }

//    open fun handleLoadingChanged(isLoading: Boolean) {
//        if (isLoading) showLoadingDialog() else hideLoadingDialog()
//    }

    override fun onAttach(context: Context?) {
        performDependencyInjection()
        super.onAttach(context)
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }


    /**
     * fragment transaction
     */

    fun findFragment(TAG: String): Fragment? {
        return activity?.supportFragmentManager?.findFragmentByTag(TAG)
    }

    fun findChildFragment(parentFragment: Fragment = this, TAG: String): Fragment? {
        return parentFragment.childFragmentManager.findFragmentByTag(TAG)
    }

    fun replaceFragment(fragment: Fragment, TAG: String?, addToBackStack: Boolean = false,
            transit: Int = -1) {
        activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, fragment, TAG)?.apply {
                    commitTransaction(this, addToBackStack, transit)
                }?.commit()
    }

    fun replaceChildFragment(parentFragment: Fragment = this, containerViewId: Int,
            fragment: Fragment, TAG: String?, addToBackStack: Boolean = false, transit: Int = -1) {
        val transaction = parentFragment.childFragmentManager.beginTransaction().replace(
                containerViewId, fragment, TAG)
        commitTransaction(transaction, addToBackStack, transit)
    }

    fun addChildFragment(parentFragment: Fragment = this, containerViewId: Int,
            targetFragment: Fragment, TAG: String?, addToBackStack: Boolean = false,
            transit: Int = -1) {
        val transaction = parentFragment.childFragmentManager.beginTransaction().add(
                containerViewId, targetFragment, TAG)
        commitTransaction(transaction, addToBackStack, transit)
    }

    fun showDialogFragment(dialogFragment: DialogFragment, TAG: String?,
            addToBackStack: Boolean = false, transit: Int = -1) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (addToBackStack) transaction?.addToBackStack(TAG)
        if (transit != -1) transaction?.setTransition(transit)
        dialogFragment.show(transaction, TAG)
    }

    private fun commitTransaction(transaction: FragmentTransaction, addToBackStack: Boolean = false,
            transit: Int = -1) {
        if (addToBackStack) transaction.addToBackStack(null)
        if (transit != -1) transaction.setTransition(transit)
        transaction.commit()
    }

    fun popChildFragment(parentFragment: Fragment = this) {
        parentFragment.childFragmentManager.popBackStack()
    }

    override fun onBack(): Boolean {
        return false
    }
}