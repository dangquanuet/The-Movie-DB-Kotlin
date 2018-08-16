package com.quanda.moviedb.ui.screen.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.quanda.moviedb.BR
import com.quanda.moviedb.R
import com.quanda.moviedb.databinding.FragmentLoginBinding
import com.quanda.moviedb.ui.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    companion object {
        const val TAG = "LoginFragment"

        fun newInstance() = LoginFragment()
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_login

    override val viewModel: LoginViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun clickLogin() {
        Log.e(TAG, "valid ${viewModel.email.value} ${viewModel.password.value}")
    }
}