package com.quanda.moviedb.base.activity

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.quanda.moviedb.base.viewmodel.BaseViewModel

abstract class BaseDataBindActivity<T : ViewDataBinding, K : BaseViewModel> : BaseActivity() {

    lateinit var binding: T
    lateinit var viewModel: K

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onActivityDestroyed()
    }

    abstract fun getLayoutId(): Int

    abstract fun initData()

}