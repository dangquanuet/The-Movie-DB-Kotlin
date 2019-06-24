package com.example.moviedb.ui.screen.login

import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentLoginBinding
import com.example.moviedb.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val layoutId: Int = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModel()
}