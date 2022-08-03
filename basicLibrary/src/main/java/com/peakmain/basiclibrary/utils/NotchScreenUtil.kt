package com.peakmain.basiclibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.DisplayCutout
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.ui.utils.SizeUtils
import java.lang.reflect.Method

/**
 * author ：Peakmain
 * createTime：2022/3/1
 * mail:2726449200@qq.com
 * describe：判断是否刘海屏工具类
 */
object NotchScreenUtil {

    private const val MIUI_NOTCH = "ro.miui.notch"
    private const val NOTCH_IN_SCREEN_VOIO = 0x00000020 // 是否有凹槽
    private var sHasNotch: Boolean? = null

    fun getNotchSize(context: Context): Int {
        when (getDeviceBrand()) {
            DEVICE_BRAND_OPPO -> if (hasOppoNotchInScreen(context)) {
                return getOppoNotchSize()
            }
            DEVICE_BRAND_HUAWEI -> if (hasHuaweiNotchInScreen(context)) {
                return getHuaweiNotchSize(context)
            }
            DEVICE_BRAND_VIVO -> if (hasVivoNotchInScreen(context)) return getVivoNotchSize()
        }
        return 0
    }

    fun hasHuaweiNotchInScreen(context: Context): Boolean {
        var ret = false
        try {
            val cl = context.classLoader
            val HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get = HwNotchSizeUtil.getMethod("hasNotchInScreen")
            ret = get.invoke(HwNotchSizeUtil) as Boolean
            Log.d("NotchScreenUtil", "this Huawei device has notch in screen？$ret")
        } catch (e: ClassNotFoundException) {
            Log.e("NotchScreenUtil", "hasNotchInScreen ClassNotFoundException", e)
        } catch (e: NoSuchMethodException) {
            Log.e("NotchScreenUtil", "hasNotchInScreen NoSuchMethodException", e)
        } catch (e: Exception) {
            Log.e("NotchScreenUtil", "hasNotchInScreen Exception", e)
        }
        return ret
    }

    @SuppressLint("PrivateApi")
    fun hasVivoNotchInScreen(context: Context): Boolean {
        var ret = false
        try {
            val cl = context.classLoader
            val ftFeature = cl.loadClass("android.util.FtFeature")
            val methods = ftFeature.declaredMethods
            for (i in methods.indices) {
                val method = methods[i]
                if (method.name.equals("isFeatureSupport", ignoreCase = true)) {
                    ret = method.invoke(ftFeature, NOTCH_IN_SCREEN_VOIO) as Boolean
                    break
                }
            }
        } catch (e: ClassNotFoundException) {
            Log.i("NotchScreenUtil", "hasNotchInVivo ClassNotFoundException")
        } catch (e: Exception) {
            Log.e("NotchScreenUtil", "hasNotchInVivo Exception")
        }
        return ret
    }

    /**
     * 获取华为刘海的高
     *
     * @param context
     * @return
     */
    fun getHuaweiNotchSize(context: Context): Int {
        var ret = intArrayOf(0, 0)
        try {
            val cl = context.classLoader
            val notchSieUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get = notchSieUtil.getMethod("getNotchSize")
            ret = get.invoke(notchSieUtil) as IntArray
        } catch (e: ClassNotFoundException) {
            Log.e("NotchScreenUtil", "getNotchSize ClassNotFoundException")
        } catch (e: NoSuchMethodException) {
            Log.e("NotchScreenUtil", "getNotchSize NoSuchMethodException")
        } catch (e: Exception) {
            Log.e("NotchScreenUtil", "getNotchSize Exception")
        }
        return ret[1]
    }

    fun hasOppoNotchInScreen(context: Context): Boolean {
        val hasNotch =
            context.packageManager.hasSystemFeature("com.oppo.feature.screen.heteromorphism")
        Log.d("NotchScreenUtil", "this OPPO device has notch in screen？$hasNotch")
        return hasNotch
    }

    @SuppressLint("PrivateApi")
    fun hasXiaomiNotchInScreen(context: Context): Boolean {
        try {
            val spClass = Class.forName("android.os.SystemProperties")
            val getMethod: Method = spClass.getDeclaredMethod(
                "getInt",
                String::class.java,
                Int::class.javaPrimitiveType
            )
            getMethod.isAccessible = true
            val hasNotch = getMethod.invoke(null, MIUI_NOTCH, 0) as Int
            return hasNotch == 1
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
    

    fun getOppoNotchSize(): Int {
        return SizeUtils.dp2px(80f)
    }


    fun getVivoNotchSize(): Int {
        return SizeUtils.dp2px(32f)
    }


    const val DEVICE_BRAND_OPPO = 0x0001
    const val DEVICE_BRAND_HUAWEI = 0x0002
    const val DEVICE_BRAND_VIVO = 0x0003

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    @SuppressLint("DefaultLocale")
    fun getDeviceBrand(): Int {
        val brand = Build.BRAND.trim { it <= ' ' }.toUpperCase()
        return when {
            brand.contains("HUAWEI") -> {
                DEVICE_BRAND_HUAWEI
            }
            brand.contains("OPPO") -> {
                DEVICE_BRAND_OPPO
            }
            brand.contains("VIVO") -> {
                DEVICE_BRAND_VIVO
            }
            else -> 0
        }
    }

}