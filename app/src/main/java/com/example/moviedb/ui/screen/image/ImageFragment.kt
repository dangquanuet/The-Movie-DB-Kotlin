package com.example.moviedb.ui.screen.image

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentImageBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.ui.base.BaseViewModel
import com.example.moviedb.ui.base.getNavController
import com.example.moviedb.utils.setSingleClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class ImageFragment : BaseFragment<FragmentImageBinding, BaseViewModel>() {

    override val layoutId: Int = R.layout.fragment_image

    override val viewModel: ImageViewModel by viewModels()

    private val args: ImageFragmentArgs by navArgs()

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        }

        viewBinding.imageRequestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }
        }

        viewBinding.imageBack.setSingleClick {
            getNavController()?.navigateUp()
        }

        viewModel.apply {
            args.imageUrl.let {
                imageUrl.value = it
            }
        }

        ioScope.launch {
            // Animation Watchdog - Make sure we don't wait longer than a second for the Glide image
            delay(1000)
            withContext(Dispatchers.Main) {
                startPostponedEnterTransition()
            }
        }

        postponeEnterTransition()
    }
}
