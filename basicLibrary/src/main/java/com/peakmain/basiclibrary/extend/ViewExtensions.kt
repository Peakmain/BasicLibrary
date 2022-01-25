package com.peakmain.basiclibrary.extend

import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation

/**
 * author ：Peakmain
 * createTime：2022/1/5
 * mail:2726449200@qq.com
 * describe：View的扩展
 */


/**
 * View的抖动动画
 * @param fromXDelta Change in X coordinate to apply at the start of the
 *        animation
 * @param toXDelta Change in X coordinate to apply at the end of the
 *        animation
 * @param fromYDelta Change in Y coordinate to apply at the start of the
 *        animation
 * @param toYDelta Change in Y coordinate to apply at the end of the
 *        animation
 * @param duration durationMillis Duration in milliseconds
 */
fun <T : View> T.shakeAnimation(
    fromXDelta: Float = 0f,
    toXDelta: Float = 5f,
    fromYDelta: Float = 0f,
    toYDelta: Float = 0f,
    duration: Long = 500
) {
    TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta).also {
        it.duration = duration
        it.interpolator = CycleInterpolator(3f)
        startAnimation(it)
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun View.setPadding(left: Int, top: Int, right: Int, bottom: Int) {
    setPadding(left, top, right, bottom)
}
