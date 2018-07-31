package com.quanda.moviedb.ui.base.fragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quanda.moviedb.ui.base.navigator.BaseNavigator
import com.quanda.moviedb.ui.base.viewmodel.BaseViewModel

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {

    lateinit var binding: ViewBinding
    lateinit var viewModel: ViewModel
    lateinit var navigator: BaseNavigator

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseNavigator) {
            navigator = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.apply {
            root.isClickable = true
            setLifecycleOwner(this@BaseFragment)
        }
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