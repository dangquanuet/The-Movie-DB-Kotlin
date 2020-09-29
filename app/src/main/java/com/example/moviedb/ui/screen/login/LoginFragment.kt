package com.example.moviedb.ui.screen.login

import androidx.fragment.app.viewModels
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentLoginBinding
import com.example.moviedb.ui.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseDialogFragment<FragmentLoginBinding, LoginViewModel>() {

    override val layoutId: Int = R.layout.fragment_login

    override val viewModel: LoginViewModel by viewModels()
}