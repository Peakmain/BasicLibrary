package com.peakmain.basiclibrary.manager

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.peakmain.basiclibrary.config.BasicLibraryConfig

/**
 * author ：Peakmain
 * createTime：2022/1/27
 * mail:2726449200@qq.com
 * describe：
 */
object AnimationManager {
    /**
     * 显示淡入淡出动画
     */
    fun showCrossFadeLoadingView(contentView: View, loadingView: View) {
        contentView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
    }

    /**
     * 隐藏淡入淡出动画
     */
    fun hideCrossFadeLoadingView(contentView: View, loadingView: View) {
        val animationDuration: Int = BasicLibraryConfig.getInstance().getApp()
            .getApplication().resources.getInteger(android.R.integer.config_shortAnimTime)
        contentView.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(animationDuration.toLong())
                .setListener(null)
        }
        loadingView.animate()
            .alpha(0f)
            .setDuration(animationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    loadingView.visibility = View.GONE
                }
            })
    }
}