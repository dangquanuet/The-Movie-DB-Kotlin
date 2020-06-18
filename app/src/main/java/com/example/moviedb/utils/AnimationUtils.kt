package com.example.moviedb.utils

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import androidx.annotation.AnimatorRes

fun Context.startObjectAnimator(
    targetView: Any?,
    @AnimatorRes objectAnimatorId: Int
): ObjectAnimator? {
    return (AnimatorInflater.loadAnimator(this, objectAnimatorId) as? ObjectAnimator)?.apply {
        target = targetView
        start()
    }
}

fun Context.startAnimatorSet(
    targetView: Any?,
    @AnimatorRes animatorSetId: Int
): AnimatorSet? {
    return (AnimatorInflater.loadAnimator(this, animatorSetId) as? AnimatorSet)?.apply {
        setTarget(targetView)
        start()
    }
}