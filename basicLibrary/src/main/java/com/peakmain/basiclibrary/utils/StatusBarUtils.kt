package com.peakmain.basiclibrary.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 * author ：Peakmain
 * createTime：2022/2/28
 * mail:2726449200@qq.com
 * describe：
 */
object StatusBarUtils {
    /**
     * @param isDark true 白底黑字 false 黑底白字
     * @param statusColor 状态栏的颜色
     * @param translucent 沉浸式效果，也就是页面延伸到状态栏之下
     */
    fun setStatusBar(
        activity: Activity,
        isDark: Boolean = false,
        statusColor: Int = Color.WHITE,
        translucent: Boolean = false
    ) {
        val window = activity.window
        val decorView = window.decorView
        var visibility = decorView.systemUiVisibility
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = statusColor
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            visibility = if (isDark) {
                //白底黑字
                visibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                //黑底白字
                visibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
        if (translucent) {
            visibility = visibility or
                    //使得页面全屏，信号，字体看不清
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    //信号，字体可见
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        }
        decorView.systemUiVisibility = visibility
    }
}