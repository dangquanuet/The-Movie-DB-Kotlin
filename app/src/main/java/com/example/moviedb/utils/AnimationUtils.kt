package com.example.moviedb.utils

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import androidx.annotation.AnimatorRes

fun Context.startObjectAnimator(
    targetView: Any?,
    @AnimatorRes objectAnimatorId: Int
): ObjectAnimator? {
    val animator = try {
        AnimatorInflater.loadAnimator(this, objectAnimatorId)
    } catch (e: Resources.NotFoundException) {
        null
    }
    return (animator as? ObjectAnimator)?.apply {
        target = targetView
        start()
    }
}

fun Context.startAnimatorSet(
    targetView: Any?,
    @AnimatorRes animatorSetId: Int
): AnimatorSet? {
    val animator = try {
        AnimatorInflater.loadAnimator(this, animatorSetId)
    } catch (e: Resources.NotFoundException) {
        null
    }
    return (animator as? AnimatorSet)?.apply {
        setTarget(targetView)
        start()
    }
}