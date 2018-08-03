package com.quanda.moviedb.ui.screen.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.quanda.moviedb.R
import com.quanda.moviedb.databinding.FragmentLoginBinding
import com.quanda.moviedb.ui.base.fragment.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(), LoginNavigator {

    companion object {
        const val TAG = "LoginFragment"

        fun newInstance() = LoginFragment()
    }

    override val layoutId: Int
        get() = R.layout.fragment_login

    override val viewModel: LoginViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
                .apply {
                    navigator = this@LoginFragment
                }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            view = this@LoginFragment
            viewModel = this@LoginFragment.viewModel
        }
    }

    fun clickLogin() {
        Log.e(TAG, "valid ${viewModel.email.value} ${viewModel.password.value}")
    }
}