package com.insiderser.popularmovies.util

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.view.updateLayoutParams
import com.insiderser.popularmovies.R
import kotlin.math.max

fun TextView.animateMaxLines(newMaxLines: Int) {

    fun measureTargetHeight(): Int {
        maxLines = newMaxLines
        measure(
            /*width=*/ MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            /*height=*/ MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        return measuredHeight
    }

    fun onAnimationEnd() {
        maxLines = newMaxLines
        updateLayoutParams { height = ViewGroup.LayoutParams.WRAP_CONTENT }
        setTag(R.id.tag_height_animation, null)
    }

    if (!isLaidOut) { // Short path: skip animations if not laid out
        onAnimationEnd()
        return
    }

    cancelMaxLinesAnimation()

    val oldMaxLines = this.maxLines
    val targetHeight = measureTargetHeight()

    this.maxLines = max(oldMaxLines, newMaxLines)
    val anim = ObjectAnimator.ofInt(this, ViewAnimationProperties.HEIGHT, height, targetHeight).apply {
        duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        doOnCancel { onAnimationEnd() }
        doOnEnd { onAnimationEnd() }
        start()
    }
    setTag(R.id.tag_height_animation, anim)
}

fun TextView.cancelMaxLinesAnimation() {
    val previousAnimator = getTag(R.id.tag_height_animation) as? Animator
    previousAnimator?.cancel()
}
