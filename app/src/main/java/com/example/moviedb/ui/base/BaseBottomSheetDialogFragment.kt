package com.example.moviedb.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.moviedb.BR
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> :
    BottomSheetDialogFragment() {

    lateinit var viewBinding: ViewBinding

    abstract val viewModel: ViewModel

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            setVariable(BR.viewModel, viewModel)
            root.isClickable = true
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
    }
}