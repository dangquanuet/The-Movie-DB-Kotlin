package com.quanda.moviedb.ui.screen.login

import android.util.Log
import com.quanda.moviedb.R
import com.quanda.moviedb.databinding.FragmentLoginBinding
import com.quanda.moviedb.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    companion object {
        const val TAG = "LoginFragment"

        fun newInstance() = LoginFragment()
    }

    override val layoutId: Int
        get() = R.layout.fragment_login

    override val viewModel by viewModel<LoginViewModel>()

    fun clickLogin() {
        Log.e(TAG, "valid ${viewModel.email.value} ${viewModel.password.value}")
    }
}