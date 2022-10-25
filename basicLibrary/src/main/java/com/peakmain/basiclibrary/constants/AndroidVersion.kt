package com.peakmain.basiclibrary.constants

import android.os.Build

/**
 * author ：Peakmain
 * createTime：2022/08/01
 * mail:2726449200@qq.com
 * describe：
 */
@SuppressWarnings("all")
object AndroidVersion {

    private const val ANDROID_13: Int = Build.VERSION_CODES.TIRAMISU
    private const val ANDROID_12: Int = Build.VERSION_CODES.S
    private const val ANDROID_11: Int = Build.VERSION_CODES.R
    private const val ANDROID_10: Int = Build.VERSION_CODES.Q
    private const val ANDROID_9: Int = Build.VERSION_CODES.P
    private const val ANDROID_8_1: Int = Build.VERSION_CODES.O_MR1
    private const val ANDROID_8: Int = Build.VERSION_CODES.O
    private const val ANDROID_7_1: Int = Build.VERSION_CODES.N_MR1
    private const val ANDROID_7: Int = Build.VERSION_CODES.N
    private const val ANDROID_6: Int = Build.VERSION_CODES.M
    private const val ANDROID_5_1: Int = Build.VERSION_CODES.LOLLIPOP_MR1
    private const val ANDROID_5: Int = Build.VERSION_CODES.LOLLIPOP
    private const val ANDROID_4_4_w: Int = Build.VERSION_CODES.KITKAT_WATCH
    private const val ANDROID_4_4: Int = Build.VERSION_CODES.KITKAT
    private const val ANDROID_4_3: Int = Build.VERSION_CODES.JELLY_BEAN_MR2
    private const val ANDROID_4_2: Int = Build.VERSION_CODES.JELLY_BEAN_MR1
    private const val ANDROID_4_1: Int = Build.VERSION_CODES.JELLY_BEAN
    private const val ANDROID_4_0: Int = Build.VERSION_CODES.ICE_CREAM_SANDWICH

    /**
     * 是否是android13及以上版本
     */
    fun isAndroid13(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_13
    }

    /**
     * 是否是 Android 12 及以上版本
     */
    fun isAndroid12(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_12
    }

    /**
     * 是否是 Android 11 及以上版本
     */
    fun isAndroid11(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_11
    }

    /**
     * 是否是 Android 10 及以上版本
     */
    fun isAndroid10(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_10
    }

    /**
     * 是否是 Android 9.0 及以上版本
     */
    fun isAndroid9(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_9
    }

    /**
     * 是否是 Android 8.1 及以上版本
     */
    fun isAndroid8_1(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_8_1
    }

    /**
     * 是否是 Android 8.0 及以上版本
     */
    fun isAndroid8(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_8
    }

    /**
     * 是否是 Android 7.0 及以上版本
     */
    fun isAndroid7(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_7
    }

    /**
     * 是否是 Android 6.0 及以上版本
     */
    fun isAndroid6(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_6
    }

    /**
     * 是否是 Android 5.0 及以上版本
     */
    fun isAndroid5_1(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_5_1
    }

    /**
     * 是否是 Android 5.0 及以上版本
     */
    fun isAndroid5(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_5
    }

    /**
     * 是否是 Android 4.4W 及以上版本
     */
    fun isAndroid4_4_w(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_4_4_w
    }

    /**
     * 是否是 Android 4.4 及以上版本
     */
    fun isAndroid4_4(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_4_4
    }

    /**
     * 是否是 Android 4.3 及以上版本
     */
    fun isAndroid4_3(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_4_3
    }

    /**
     * 是否是 Android 4.2 及以上版本
     */
    fun isAndroid4_2(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_4_2
    }

    /**
     * 是否是 Android 4.0 及以上版本
     */
    fun isAndroid4(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_4_0
    }
}