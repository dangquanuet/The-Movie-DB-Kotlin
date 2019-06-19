package com.example.moviedb.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.SystemClock
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.moviedb.BuildConfig
import com.example.moviedb.data.constants.Constants
import java.io.File

@BindingAdapter("onRefreshListener")
fun SwipeRefreshLayout.customRefreshListener(listener: SwipeRefreshLayout.OnRefreshListener?) {
    if (listener != null) setOnRefreshListener(listener)
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.customRefreshing(refreshing: Boolean?) {
    isRefreshing = refreshing == true
}

@BindingAdapter("onScrollListener")
fun RecyclerView.customScrollListener(listener: RecyclerView.OnScrollListener?) {
    if (listener != null) addOnScrollListener(listener)
}

@BindingAdapter("glideSrc")
fun ImageView.setGlideSrc(@DrawableRes src: Int?) {
    Glide.with(context).load(src).into(this)
}

@BindingAdapter("loadUri")
fun ImageView.loadLocalImage(uri: Uri?) {
    Glide.with(context).load(uri).into(this)
}

@BindingAdapter(value = ["loadImageLocal"])
fun ImageView.loadLocalImage(imageName: String?) {
    if (imageName.isNullOrBlank().not()) {
        Glide.with(context)
            .load(resources.getIdentifier(imageName, "drawable", BuildConfig.APPLICATION_ID))
            .into(this)
    }
}

@BindingAdapter(
    value = ["loadImage", "placeholder", "centerCrop", "fitCenter", "circleCrop", "cacheSource", "animation", "large"],
    requireAll = false
)
fun ImageView.loadImage(
    url: String? = "", placeHolder: Drawable?,
    centerCrop: Boolean = false, fitCenter: Boolean = false, circleCrop: Boolean = false,
    isCacheSource: Boolean = false, animation: Boolean = false, isLarge: Boolean = false
) {
    if (url.isNullOrBlank()) {
        setImageDrawable(placeHolder)
        return
    }
    val urlWithHost =
        (if (isLarge) BuildConfig.LARGE_IMAGE_URL else BuildConfig.SMALL_IMAGE_URL) + url
    val requestBuilder = GlideApp.with(context).load(urlWithHost)
    val requestOptions = RequestOptions().diskCacheStrategy(
        if (isCacheSource) DiskCacheStrategy.DATA else DiskCacheStrategy.RESOURCE
    )
        .placeholder(placeHolder)

    if (animation.not()) requestOptions.dontAnimate()
    if (centerCrop) requestOptions.centerCrop()
    if (fitCenter) requestOptions.fitCenter()
    if (circleCrop) requestOptions.circleCrop()
    val file = File(urlWithHost)
    if (file.exists()) {
        requestOptions.signature(ObjectKey(file.lastModified().toString()))
    }
    requestBuilder.apply(requestOptions).into(this)
}

@BindingAdapter("clickSafe")
fun View.setClickSafe(listener: View.OnClickListener?) {
    setOnClickListener(object : View.OnClickListener {
        var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < Constants.THRESHOLD_CLICK_TIME) {
                return
            }
            listener?.onClick(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

@BindingAdapter("onSingleClick")
fun View.setSingleClick(execution: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        var lastClickTime: Long = 0

        override fun onClick(p0: View?) {
            if (SystemClock.elapsedRealtime() - lastClickTime < Constants.THRESHOLD_CLICK_TIME) {
                return
            }
            lastClickTime = SystemClock.elapsedRealtime()
            execution.invoke()
        }
    })
}

@BindingAdapter("loadUrl")
fun WebView.loadWebUrl(url: String?) {
    url?.apply {
        loadUrl(url)
    }
}

@BindingAdapter("backgroundTint")
fun TextView.customBackgroundTint(color: Int?) {
    if (color == null) return
    background?.setTint(color)
}

@BindingAdapter("tint")
fun ImageView.customTint(color: Int?) {
    if (color == null) return
    imageTintList = ColorStateList.valueOf(color)
}