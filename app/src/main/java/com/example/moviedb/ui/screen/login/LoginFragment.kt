package com.example.moviedb.ui.screen.login

import android.util.Log
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentLoginBinding
import com.example.moviedb.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    companion object {
        const val TAG = "LoginFragment"

        fun newInstance() = LoginFragment()
    }

    override val layoutId: Int = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModel()

    fun clickLogin() {
        Log.e(TAG, "valid ${viewModel.email.value} ${viewModel.password.value}")
    }
}