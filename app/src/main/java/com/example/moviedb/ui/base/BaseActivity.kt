package com.example.moviedb.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moviedb.BR
import com.example.moviedb.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseActivity<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> :
    AppCompatActivity() {

    protected lateinit var viewBinding: ViewBinding
    protected abstract val viewModel: ViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate ${javaClass.simpleName}")
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding.apply {
            setVariable(BR.viewModel, viewModel)
            viewBinding.lifecycleOwner = this@BaseActivity
            root.isClickable = true
            executePendingBindings()
        }
        observeErrorEvent()
    }

    private fun observeErrorEvent() {
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
        if (isLoading) showLoadingDialog()
        else dismissLLoadingDialog()
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
                dismissShowingDialog()
            }
        }
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank().not()) {
            showDialog(
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
        Timber.i("onStart ${javaClass.simpleName}")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume ${javaClass.simpleName}")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause ${javaClass.simpleName}")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop ${javaClass.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy ${javaClass.simpleName}")
        dismissLLoadingDialog()
        dismissShowingDialog()
    }

    /**
     * true if showing high priority dialog (example: force update app)
     */
    private var isShowHighPriorityDialog = false
    private var loadingDialog: AlertDialog? = null

    fun showLoadingDialog(
        cancelable: Boolean = false,
        canceledOnTouchOutside: Boolean = false
    ): AlertDialog? = when {
        isShowHighPriorityDialog -> null
        loadingDialog?.isShowing == true -> loadingDialog
        else -> {
            MaterialAlertDialogBuilder(this).apply {
                setView(R.layout.layout_loading_dialog)
            }.create().let { dialog ->
                dialog.setCancelable(cancelable)
                dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                loadingDialog = dialog
                dialog.show()
                dialog
            }
        }
    }

    fun dismissLLoadingDialog() {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }

    private var showingDialog: Dialog? = null

    fun showDialog(
        title: String? = null,
        message: String? = null,
        firstText: String? = null,
        firstListener: (() -> Unit)? = null,
        secondText: String? = null,
        secondListener: (() -> Unit)? = null,
        dismissListener: (() -> Unit)? = null,
        cancelable: Boolean = false,
        canceledOnTouchOutside: Boolean = false
    ): AlertDialog? = when {
        isShowHighPriorityDialog -> null
        else -> {
            MaterialAlertDialogBuilder(this).apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton(firstText) { dialog, which ->
                    firstListener?.invoke()
                }
                setNegativeButton(secondText) { dialog, which ->
                    secondListener?.invoke()
                }
                setCancelable(cancelable)
            }.create().let { dialog ->
                dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
                if (showingDialog?.isShowing == true) {
                    showingDialog?.dismiss()
                }
                dialog.setOnDismissListener {
                    dismissListener?.invoke()
                    showingDialog = null
                }
                showingDialog = dialog
                dialog.show()
                dialog
            }
        }
    }

    fun showForceUpdateDialog() {
        val forceUpdateDialog = showDialog(
            title = getString(R.string.force_update_app),
            dismissListener = {
                isShowHighPriorityDialog = false
            }
        )
        if (forceUpdateDialog != null) {
            isShowHighPriorityDialog = true
        }
    }

    /**
     * show dialog with custom layout
     */
    /*fun showCustomDialog(
        title: String? = null,
        message: String? = null,
        firstText: String? = null,
        firstListener: (() -> Unit)? = null,
        secondText: String? = null,
        secondListener: (() -> Unit)? = null,
        showCloseButton: Boolean = false,
        dismissListener: (() -> Unit)? = null,
        cancelable: Boolean = false,
        canceledOnTouchOutside: Boolean = false
    ): AlertDialog? = when {
        isShowHighPriorityDialog -> null
        else -> {
            MaterialAlertDialogBuilder(this).apply {
                setCancelable(cancelable)
            }.create().let { dialog ->
                dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
                val viewBinding = DataBindingUtil.inflate<DialogCustomBinding>(
                    LayoutInflater.from(this), R.layout.dialog_custom, null, false
                )
                viewBinding.showClose = showCloseButton
                viewBinding.title = title
                viewBinding.message = message
                viewBinding.firstText = firstText
                viewBinding.firstTextAction.setSingleClick {
                    dialog.dismiss()
                    firstListener?.invoke()
                }
                viewBinding.secondText = secondText
                viewBinding.secondTextAction.setSingleClick {
                    dialog.dismiss()
                    secondListener?.invoke()
                }
                viewBinding.imageClose.setSingleClick {
                    dialog.dismiss()
                }
                if (showingDialog?.isShowing == true) {
                    showingDialog?.dismiss()
                }
                dialog.setOnDismissListener {
                    dismissListener?.invoke()
                    showingDialog = null
                }
                showingDialog = dialog
                dialog.setView(viewBinding.root)
                dialog.show()
                return dialog
            }
        }
    }*/

    fun dismissShowingDialog() {
        if (isShowHighPriorityDialog.not() && showingDialog?.isShowing == true) {
            showingDialog?.dismiss()
            showingDialog = null
        }
    }
}
