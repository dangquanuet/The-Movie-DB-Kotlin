package com.example.moviedb.ui.screen.image

import android.os.Build
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentImageBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.utils.setSingleClick
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.android.synthetic.main.fragment_movie_detail.image_back
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageFragment : BaseFragment<FragmentImageBinding, BaseViewModel>() {

    override val layoutId: Int = R.layout.fragment_image

    override val viewModel: ImageViewModel by viewModel()

    private val args: ImageFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        image_back?.setSingleClick {
            findNavController().navigateUp()
        }

        viewModel.apply {
            args.imageUrl.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    image?.transitionName = it
                }

                imageUrl.value = it
            }
        }
    }
}