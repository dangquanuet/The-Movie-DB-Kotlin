package com.quanda.moviedb.ui.screen.main.login

import android.arch.lifecycle.ViewModelProviders
import android.util.Log
import com.quanda.moviedb.R
import com.quanda.moviedb.databinding.ActivityLoginBinding
import com.quanda.moviedb.ui.base.activity.BaseDataLoadActivity

class LoginActivity : BaseDataLoadActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

    override fun getLayoutId() = R.layout.activity_login

    override fun initViewModel(): LoginViewModel {
        return ViewModelProviders.of(this).get(LoginViewModel::class.java)
                .apply {
                    navigator = this@LoginActivity
                }
    }

    override fun initData() {
        super.initData()
        binding.apply {
            view = this@LoginActivity
            viewModel = this@LoginActivity.viewModel
        }
    }

    fun clickLogin() {
        Log.e("LoginActivity ", "valid ${viewModel.email.value} ${viewModel.password.value}")
    }
}