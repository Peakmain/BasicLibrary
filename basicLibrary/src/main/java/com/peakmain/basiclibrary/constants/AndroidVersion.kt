package com.peakmain.basiclibrary.constants

import android.os.Build
import androidx.annotation.IntDef

/**
 * author ：Peakmain
 * createTime：2022/08/01
 * mail:2726449200@qq.com
 * describe：
 */
@SuppressWarnings("all")
object AndroidVersion {

    const val ANDROID_13: Int = Build.VERSION_CODES.TIRAMISU
    const val ANDROID_12: Int = Build.VERSION_CODES.S
    const val ANDROID_11: Int = Build.VERSION_CODES.R
    const val ANDROID_10: Int = Build.VERSION_CODES.Q
    const val ANDROID_9: Int = Build.VERSION_CODES.P
    const val ANDROID_8_1: Int = Build.VERSION_CODES.O_MR1
    const val ANDROID_8: Int = Build.VERSION_CODES.O
    const val ANDROID_7_1: Int = Build.VERSION_CODES.N_MR1
    const val ANDROID_7: Int = Build.VERSION_CODES.N
    const val ANDROID_6: Int = Build.VERSION_CODES.M
    const val ANDROID_5_1: Int = Build.VERSION_CODES.LOLLIPOP_MR1
    const val ANDROID_5: Int = Build.VERSION_CODES.LOLLIPOP
    const val ANDROID_4_4_w: Int = Build.VERSION_CODES.KITKAT_WATCH
    const val ANDROID_4_4: Int = Build.VERSION_CODES.KITKAT
    const val ANDROID_4_3: Int = Build.VERSION_CODES.JELLY_BEAN_MR2
    const val ANDROID_4_2: Int = Build.VERSION_CODES.JELLY_BEAN_MR1
    const val ANDROID_4_1: Int = Build.VERSION_CODES.JELLY_BEAN
    const val ANDROID_4_0: Int = Build.VERSION_CODES.ICE_CREAM_SANDWICH

    @IntDef(
        ANDROID_13,
        ANDROID_12,
        ANDROID_11,
        ANDROID_10,
        ANDROID_9,
        ANDROID_8_1,
        ANDROID_8,
        ANDROID_7_1,
        ANDROID_7,
        ANDROID_6,
        ANDROID_5_1,
        ANDROID_5,
        ANDROID_4_4_w,
        ANDROID_4_4,
        ANDROID_4_3,
        ANDROID_4_2,
        ANDROID_4_1,
        ANDROID_4_0
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class PermissionVersion

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

    fun isAndroid7_1(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_7_1
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

    fun isAndroid4_1(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_4_1
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