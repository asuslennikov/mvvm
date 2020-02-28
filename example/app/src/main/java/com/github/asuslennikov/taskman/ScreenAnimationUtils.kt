package com.github.asuslennikov.taskman

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import kotlin.math.hypot

object ScreenAnimationUtils {

    data class AnimationData(
        val centerView: View,
        val targetView: View,
        val firstColor: Int,
        val secondColor: Int,
        val duration: Long,
        val reversed: Boolean = false
    )

    @JvmStatic
    fun startCircularRevealAnimation(animData: AnimationData, onEndAction: ((AnimationData) -> Unit)? = null) {
        val centerViewPosition = IntArray(2)
        animData.centerView.getLocationInWindow(centerViewPosition)
        val animXCenter = centerViewPosition[0] + animData.centerView.width / 2
        val animYCenter = centerViewPosition[1]
        val startRadius = animData.centerView.width / 2.toFloat()
        val endRadius = hypot(animXCenter.toDouble(), animYCenter.toDouble()).toFloat()
        ViewAnimationUtils.createCircularReveal(
            animData.targetView,
            animXCenter,
            animYCenter,
            if (animData.reversed) endRadius else startRadius,
            if (animData.reversed) startRadius else endRadius
        ).apply {
            interpolator = DecelerateInterpolator(1.5f)
            duration = animData.duration
            onEndAction?.run {
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        invoke(animData)
                    }
                })
            }
            start()
        }
        startBackgroundColorAnimation(animData)
    }

    @JvmStatic
    fun startBackgroundColorAnimation(animData: AnimationData) {
        val context = animData.targetView.context
        val bgColorAnim = ValueAnimator()
        bgColorAnim.setIntValues(
            ContextCompat.getColor(context, if (animData.reversed) animData.secondColor else animData.firstColor),
            ContextCompat.getColor(context, if (animData.reversed) animData.firstColor else animData.secondColor)
        )
        bgColorAnim.setEvaluator(ArgbEvaluator())
        bgColorAnim.addUpdateListener { valueAnimator ->
            animData.targetView.setBackgroundColor((valueAnimator.animatedValue as Int))
        }
        bgColorAnim.duration = animData.duration
        bgColorAnim.start()
    }
}