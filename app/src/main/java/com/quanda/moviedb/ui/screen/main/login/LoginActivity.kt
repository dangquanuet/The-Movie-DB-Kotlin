package com.quanda.moviedb.ui.screen.main.login

import android.arch.lifecycle.ViewModelProviders
import android.util.Log
import com.quanda.moviedb.R
import com.quanda.moviedb.ui.base.activity.BaseDataLoadActivity
import com.quanda.moviedb.databinding.ActivityLoginBinding

class LoginActivity : BaseDataLoadActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

    override fun getLayoutId() = R.layout.activity_login

    override fun initViewModel(): LoginViewModel {
        return ViewModelProviders.of(this, LoginViewModel.CustomFactory(application, this)).get(
                LoginViewModel::class.java)
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