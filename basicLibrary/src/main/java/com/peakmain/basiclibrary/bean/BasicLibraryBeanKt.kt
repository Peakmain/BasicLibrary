package com.peakmain.basiclibrary.bean

import android.graphics.drawable.Drawable

/**
 * author ：Peakmain
 * createTime：2022/3/8
 * mail:2726449200@qq.com
 * describe：
 */
data class AppManagerBean(
    val packageName: String,
    val appName: String,
    val appIcon: Drawable,
    val firstRunName: String,
    val isSystemApp: Boolean
)