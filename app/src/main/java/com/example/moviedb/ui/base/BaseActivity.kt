package com.example.moviedb.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.example.moviedb.BR
import com.example.moviedb.R
import com.example.moviedb.utils.dismissLLoadingDialog
import com.example.moviedb.utils.showDialog
import com.example.moviedb.utils.showLoadingDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseActivity<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> :
    AppCompatActivity() {

    protected lateinit var viewBinding: ViewBinding
    protected abstract val viewModel: ViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            viewModel.isLoading.collectLatest {
                handleLoading(it)
            }
            viewModel.errorMessage.collectLatest {
                handleErrorMessage(it)
            }
            viewModel.noInternetConnectionEvent.collectLatest {
                handleErrorMessage(getString(R.string.no_internet_connection))
            }
            viewModel.connectTimeoutEvent.collectLatest {
                handleErrorMessage(getString(R.string.connect_timeout))
            }
            viewModel.forceUpdateAppEvent.collectLatest {
                handleErrorMessage(getString(R.string.force_update_app))
            }
            viewModel.serverMaintainEvent.collectLatest {
                handleErrorMessage(getString(R.string.server_maintain_message))
            }
            viewModel.unknownErrorEvent.collectLatest {
                handleErrorMessage(getString(R.string.unknown_error))
            }
        }
    }

    /**
     * override this if not use loading dialog (example progress bar)
     */
    protected open fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else dismissLLoadingDialog()
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLLoadingDialog()
        showDialog(
            message = message,
            textPositive = getString(R.string.ok)
        )
    }
}
