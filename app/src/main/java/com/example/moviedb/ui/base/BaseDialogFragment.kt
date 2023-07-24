package com.example.moviedb.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moviedb.BR
import com.example.moviedb.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseDialogFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> :
    DialogFragment() {

    protected lateinit var viewBinding: ViewBinding
    protected abstract val viewModel: ViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected val baseActivity by lazy { activity as BaseActivity<*, *> }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView ${javaClass.simpleName}")
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding.apply {
            setVariable(BR.viewModel, viewModel)
            viewBinding.lifecycleOwner = viewLifecycleOwner
            root.isClickable = true
            executePendingBindings()
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated ${javaClass.simpleName}")
        observerEvents()
    }

    private fun observerEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    handleLoading(isLoading = uiState.isLoading)
                    handleError(errorType = uiState.errorType)
                }
            }
        }
    }

    /**
     * override this if not use loading dialog (example progress bar)
     */
    protected open fun handleLoading(isLoading: Boolean) {
        if (isLoading) baseActivity.showLoadingDialog()
        else baseActivity.dismissLLoadingDialog()
    }

    protected fun handleError(errorType: ErrorType?) {
        when (errorType) {
            ErrorType.NoInternetConnection -> {
                handleErrorMessage(getString(R.string.no_internet_connection))
            }

            ErrorType.ConnectTimeout -> {
                handleErrorMessage(getString(R.string.connect_timeout))
            }

            ErrorType.UnAuthorized -> {
                handleErrorMessage(getString(R.string.unknown_error))
            }

            ErrorType.ForceUpdateApp -> {
                handleErrorMessage(getString(R.string.force_update_app))
            }

            ErrorType.ServerMaintain -> {
                handleErrorMessage(getString(R.string.server_maintain_message))
            }

            is ErrorType.UnknownError -> {
                handleErrorMessage(getString(R.string.unknown_error))
            }

            else -> {
                baseActivity.dismissShowingDialog()
            }
        }
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank().not()) {
            baseActivity.showDialog(
                message = message,
                firstText = getString(R.string.ok),
                dismissListener = {
                    viewModel.hideError()
                }
            )
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart ${javaClass.simpleName}")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume ${javaClass.simpleName}")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause ${javaClass.simpleName}")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop ${javaClass.simpleName}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView ${javaClass.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy ${javaClass.simpleName}")
    }

    fun navigateUp() {
        getNavController()?.navigateUp()
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
