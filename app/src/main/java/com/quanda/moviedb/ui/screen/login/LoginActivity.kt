package com.quanda.moviedb.ui.screen.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.quanda.moviedb.R
import com.quanda.moviedb.databinding.ActivityLoginBinding
import com.quanda.moviedb.ui.base.activity.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

    override val layoutId: Int
        get() = R.layout.activity_login

    override val viewModel: LoginViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
                .apply {
                    navigator = this@LoginActivity
                }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            view = this@LoginActivity
            viewModel = this@LoginActivity.viewModel
        }
    }

    fun clickLogin() {
        Log.e("LoginActivity ", "valid ${viewModel.email.value} ${viewModel.password.value}")
    }
}