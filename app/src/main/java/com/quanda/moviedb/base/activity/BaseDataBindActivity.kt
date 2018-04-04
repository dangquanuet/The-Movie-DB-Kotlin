package com.quanda.moviedb.base.activity

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.quanda.moviedb.base.viewmodel.BaseViewModel

abstract class BaseDataBindActivity<View : ViewDataBinding, ViewModel : BaseViewModel> : BaseActivity() {

    lateinit var binding: View
    lateinit var viewModel: ViewModel

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