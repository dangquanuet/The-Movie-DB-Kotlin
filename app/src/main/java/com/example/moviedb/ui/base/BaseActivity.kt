package com.example.moviedb.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.moviedb.BR
import com.example.moviedb.R
import com.example.moviedb.ui.screen.MainActivity
import com.example.moviedb.utils.dismissLLoadingDialog
import com.example.moviedb.utils.showDialog
import com.example.moviedb.utils.showLoadingDialog
import kotlin.system.exitProcess

abstract class BaseActivity<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> :
    AppCompatActivity() {

    protected lateinit var viewBinding: ViewBinding

    protected abstract val viewModel: ViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleUncaughtException()

        if (::viewBinding.isInitialized.not()) {
            viewBinding = DataBindingUtil.setContentView(this, layoutId)
            viewBinding.setVariable(BR.viewModel, viewModel)
        }

        observeErrorEvent()
    }

    protected fun observeErrorEvent() {
        viewModel.apply {
            isLoading.observe(this@BaseActivity, Observer {
                handleLoading(it == true)
            })
            errorMessage.observe(this@BaseActivity, Observer {
                handleErrorMessage(it)
            })
            noInternetConnectionEvent.observe(this@BaseActivity, Observer {
                handleErrorMessage(getString(R.string.no_internet_connection))
            })
            connectTimeoutEvent.observe(this@BaseActivity, Observer {
                handleErrorMessage(getString(R.string.connect_timeout))
            })
            forceUpdateAppEvent.observe(this@BaseActivity, Observer {
                handleErrorMessage(getString(R.string.force_update_app))
            })
            serverMaintainEvent.observe(this@BaseActivity, Observer {
                handleErrorMessage(getString(R.string.server_maintain_message))
            })
            unknownErrorEvent.observe(this@BaseActivity, Observer {
                handleErrorMessage(getString(R.string.unknown_error))
            })
        }
    }

    /**
     * override this if not use loading dialog (example progress bar)
     */
    open fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoading() else dismissLLoadingDialog()
    }

    fun showLoading() {
        showLoadingDialog()
    }

    fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return

        dismissLLoadingDialog()

        showDialog(
            message = message,
            textPositive = getString(R.string.ok)
        )
    }

    /**
     * prevent uncaught exception to crash app
     * restart app for better UX
     */
    protected fun handleUncaughtException() {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK
            )
            application?.startActivity(intent)
            try {
                exitProcess(2);
            } catch (e: Exception) {
                application?.startActivity(intent)
            }
        }
    }
}