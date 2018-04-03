package com.quanda.moviedb.base.fragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quanda.moviedb.base.navigator.BaseNavigator
import com.quanda.moviedb.base.viewmodel.BaseViewModel

abstract class BaseDataBindFragment<T : ViewDataBinding, K : BaseViewModel> : BaseFragment() {

    lateinit var binding: T
    lateinit var viewModel: K
    lateinit var navigator: BaseNavigator

    abstract fun getLayoutId(): Int
    abstract fun initData()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseNavigator) {
            navigator = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.root.isClickable = true
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onActivityDestroyed()
    }


}