package com.example.moviedb.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.moviedb.BR
import com.example.moviedb.R
import com.example.moviedb.utils.showDialog
import com.example.moviedb.utils.showLoadingDialog

abstract class BaseDialogFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> :
    DialogFragment() {

    protected lateinit var viewBinding: ViewBinding

    protected abstract val viewModel: ViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int

    private var loadingDialog: AlertDialog? = null
    private var messageDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (::viewBinding.isInitialized.not()) {
            viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            viewBinding.apply {
                setVariable(BR.viewModel, viewModel)
                root.isClickable = true
                lifecycleOwner = viewLifecycleOwner
                executePendingBindings()
            }
        }
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner, Observer {
                handleLoading(it == true)
            })
            errorMessage.observe(viewLifecycleOwner, Observer {
                handleErrorMessage(it)
            })
            noInternetConnectionEvent.observe(viewLifecycleOwner, Observer {
                handleErrorMessage(getString(R.string.no_internet_connection))
            })
            connectTimeoutEvent.observe(viewLifecycleOwner, Observer {
                handleErrorMessage(getString(R.string.connect_timeout))
            })
            forceUpdateAppEvent.observe(viewLifecycleOwner, Observer {
                handleErrorMessage(getString(R.string.force_update_app))
            })
            serverMaintainEvent.observe(viewLifecycleOwner, Observer {
                handleErrorMessage(getString(R.string.server_maintain_message))
            })
        }
    }

    /**
     * override this if not use loading dialog (example progress bar)
     */
    open fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else dismissLLoadingDialog()
    }

    fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = context?.showLoadingDialog()
        } else {
            loadingDialog?.show()
        }
    }

    fun dismissLLoadingDialog() {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
    }

    fun handleErrorMessage(message: String) {
        dismissLLoadingDialog()

        if (messageDialog?.isShowing == true) {
            messageDialog?.dismiss()
        }

        messageDialog = context?.showDialog(
            message = message,
            textPositive = getString(R.string.ok)
        )
    }

    /**
     * show dialog and dismiss safety when fragment destroy
     */
    fun showDialogSafe(dialog: AlertDialog?) {
        if (messageDialog?.isShowing == true) {
            messageDialog?.dismiss()
        }

        messageDialog = dialog
    }

    override fun onDestroy() {
        loadingDialog?.dismiss()
        messageDialog?.dismiss()
        super.onDestroy()
    }

    fun navigateUp() {
        findNavController().navigateUp()
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

    fun addFragment(
        fragment: Fragment, TAG: String?, addToBackStack: Boolean = false,
        transit: Int = -1
    ) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.container, fragment, TAG)
            ?.apply {
                commitTransaction(this, addToBackStack, transit)
            }
    }

    fun replaceFragment(
        fragment: Fragment, TAG: String?, addToBackStack: Boolean = false,
        transit: Int = -1
    ) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, fragment, TAG)
            ?.apply {
                commitTransaction(this, addToBackStack, transit)
            }
    }

    fun replaceChildFragment(
        parentFragment: Fragment = this, containerViewId: Int,
        fragment: Fragment, TAG: String?, addToBackStack: Boolean = false, transit: Int = -1
    ) {
        val transaction = parentFragment.childFragmentManager.beginTransaction().replace(
            containerViewId, fragment, TAG
        )
        commitTransaction(transaction, addToBackStack, transit)
    }

    fun addChildFragment(
        parentFragment: Fragment = this, containerViewId: Int,
        fragment: Fragment, TAG: String?, addToBackStack: Boolean = false, transit: Int = -1
    ) {
        val transaction = parentFragment.childFragmentManager.beginTransaction().add(
            containerViewId, fragment, TAG
        )
        commitTransaction(transaction, addToBackStack, transit)
    }

    fun showDialogFragment(
        dialogFragment: DialogFragment, TAG: String?,
        addToBackStack: Boolean = false, transit: Int = -1
    ) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (addToBackStack) transaction?.addToBackStack(TAG)
        if (transit != -1) transaction?.setTransition(transit)
        if (transaction != null) {
            dialogFragment.show(transaction, TAG)
        }
    }

    private fun commitTransaction(
        transaction: FragmentTransaction, addToBackStack: Boolean = false,
        transit: Int = -1
    ) {
        if (addToBackStack) transaction.addToBackStack(null)
        if (transit != -1) transaction.setTransition(transit)
        transaction.commit()
    }

    fun popChildFragment(parentFragment: Fragment = this) {
        parentFragment.childFragmentManager.popBackStack()
    }

}