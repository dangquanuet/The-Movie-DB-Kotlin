package com.example.moviedb.ui.screen.login

import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentLoginBinding
import com.example.moviedb.ui.base.BaseDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseDialogFragment<FragmentLoginBinding, LoginViewModel>() {

    override val layoutId: Int = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModel()
}