package com.peakmain.basiclibrary.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import java.util.*

/**
 * author ：Peakmain
 * createTime：1/22/22
 * mail:2726449200@qq.com
 * describe：手机系统判断
 */
object OSUtils {
    private const val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private const val KEY_EMUI_VERSION_NAME = "ro.build.version.emui"
    private const val KEY_DISPLAY = "ro.build.display.id"

    /**
     * 判断是否为miui
     *
     * @return the boolean
     */
    val isMIUI: Boolean
        get() {
            val property = getSystemProperty(KEY_MIUI_VERSION_NAME, "")
            return !TextUtils.isEmpty(property)
        }

    /**
     * 判断miui版本是否大于等于6
     *
     * @return the boolean
     */
    val isMIUI6Later: Boolean
        get() {
            val version = mIUIVersion
            val num: Int
            return if (version.isNotEmpty()) {
                try {
                    num = Integer.valueOf(version.substring(1))
                    num >= 6
                } catch (e: NumberFormatException) {
                    false
                }
            } else false
        }

    /**
     * 获得miui的版本
     *
     * @return the miui version
     */
    val mIUIVersion: String
        get() = if (isMIUI) getSystemProperty(
            KEY_MIUI_VERSION_NAME,
            ""
        ) else ""

    /**
     * 判断是否为emui
     *
     */
    val isEMUI: Boolean
        get() {
            val property = getSystemProperty(KEY_EMUI_VERSION_NAME, "")
            return !TextUtils.isEmpty(property)
        }

    /**
     * 得到emui的版本
     */
    val eMUIVersion: String
        get() = if (isEMUI) getSystemProperty(
            KEY_EMUI_VERSION_NAME,
            ""
        ) else ""

    /**
     * 判断是否为emui3.1版本
     */
    val isEMUI3_1: Boolean
        get() {
            val property = eMUIVersion
            return "EmotionUI 3" == property || property.contains("EmotionUI_3.1")
        }

    /**
     * 判断是否为emui3.0版本
     */
    val isEMUI3_0: Boolean
        get() {
            val property = eMUIVersion
            return property.contains("EmotionUI_3.0")
        }

    /**
     * 判断是否为emui3.x版本
     */
    val isEMUI3_x: Boolean
        get() = isEMUI3_0 || isEMUI3_1

    /**
     * 判断是否为flymeOS
     */
    val isFlymeOS: Boolean
        get() = flymeOSFlag.lowercase(Locale.getDefault()).contains("flyme")

    /**
     * 判断flymeOS的版本是否大于等于4
     */
    val isFlymeOS4Later: Boolean
        get() {
            val version = flymeOSVersion
            val num: Int
            return if (version.isNotEmpty()) {
                try {
                    num = if (version.lowercase(Locale.getDefault()).contains("os")) {
                        Integer.valueOf(version.substring(9, 10))
                    } else {
                        Integer.valueOf(version.substring(6, 7))
                    }
                    num >= 4
                } catch (e: NumberFormatException) {
                    false
                }
            } else false
        }

    /**
     * 判断flymeOS的版本是否等于5
     */
    val isFlymeOS5: Boolean
        get() {
            val version = flymeOSVersion
            val num: Int
            return if (version.isNotEmpty()) {
                try {
                    num = if (version.lowercase(Locale.getDefault()).contains("os")) {
                        Integer.valueOf(version.substring(9, 10))
                    } else {
                        Integer.valueOf(version.substring(6, 7))
                    }
                    num == 5
                } catch (e: NumberFormatException) {
                    false
                }
            } else false
        }

    /**
     * 得到flymeOS的版本
     */
    val flymeOSVersion: String
        get() = if (isFlymeOS) getSystemProperty(KEY_DISPLAY, "") else ""

    private val flymeOSFlag: String
        get() = getSystemProperty(KEY_DISPLAY, "")

    private fun getSystemProperty(key: String, defaultValue: String): String {
        try {
            @SuppressLint("PrivateApi") val clz =
                Class.forName("android.os.SystemProperties")
            val method =
                clz.getMethod("get", String::class.java, String::class.java)
            return method.invoke(clz, key, defaultValue) as String
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }
}