package com.peakmain.basiclibrary.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import java.lang.reflect.InvocationTargetException
import java.util.regex.Pattern


/**
 * author ：Peakmain
 * createTime：2022/1/7
 * mail:2726449200@qq.com
 * describe：
 */
object SystemUtils {
    /**
     * 获取手机系统SDK版本
     *
     * @return 如API 23 则返回 23
     */
    fun getSdkVersion(): Int = Build.VERSION.SDK_INT

    /**
     * 获取手机系统版本号
     *
     * @return 形如2.3.3
     */
    fun getDeviceSystemVersion(): String = Build.VERSION.RELEASE

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    fun getDeviceModel(): String = Build.MODEL

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    fun getDeviceBrand(): String = Build.BRAND

    /**
     * 返回当前程序版本名
     */
    fun getAppVersionName(context: Context): String {
        var versionName = ""
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versionName = pi.versionName
            if (versionName == null || versionName.isEmpty()) {
                return ""
            }
        } catch (ignored: Exception) {
        }
        return versionName
    }

    fun checkMobile(mobile: String?): Boolean {
        val regex = "(\\+\\d+)?1\\d{10}$"
        return Pattern.matches(regex, mobile)
    }

    /**
     * 获取SIM卡运营商
     *
     * @param context
     * @return
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission("android.permission.READ_PRIVILEGED_PHONE_STATE")
    fun getOperators(context: Context): String? {
        val tm = context
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var operator: String? = null
        val IMSI = tm.subscriberId
        if (IMSI == null || IMSI == "") {
            return null
        }
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            operator = "中国移动"
        } else if (IMSI.startsWith("46001")) {
            operator = "中国联通"
        } else if (IMSI.startsWith("46003")) {
            operator = "中国电信"
        }
        return operator
    }

    fun getProcessName(context: Context): String? {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (it in manager.runningAppProcesses) {
            if (it.pid == android.os.Process.myPid()) {
                return it.processName
            }
        }
        return null
    }

    fun isGoogleFoldDevice() =
        Build.BRAND.equals("google", true) && Build.DEVICE.equals("generic_x86", true)


    fun isHuaWeiFoldDevice() =
        Build.BRAND.equals("huawei", true) && (Build.DEVICE.equals(
            "MateX",
            true
        ) || Build.DEVICE.equals("HWPAL", true))

    fun isSamsungFold() =
        Build.BRAND.equals("samsung", true) && Build.DEVICE.equals("Galaxy Z Fold2", true)

    fun isVivoFoldDevice(): Boolean = Build.BRAND.equals("vivo", true) && isVivoFoldableDevice()
    private fun isVivoFoldableDevice(): Boolean {
        try {
            val c = Class.forName("android.util.FtDeviceInfo")
            val m = c.getMethod("getDeviceType")
            val dType = m.invoke(c)
            return "foldable" == dType
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun isXiaomiFoldDevice(): Boolean {
        if (!Build.BRAND.equals("xiaomi", true)) return false;
        try {
            // 通过反射获取systemProperties类
            val systemProperties = Class.forName("android.os.SystemProperties")
            //获取SystemProperties 的getInt方法
            val method = systemProperties.getMethod(
                "getInt",
                String::class.java,
                Int::class.javaPrimitiveType
            )
            // 调用 getInt方法对persist.sys.muiltdisplay_type 属性值来进行判断
            return method.invoke(null, "persist.sys.muiltdisplay_type", 0) as Int == 2
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        return false
    }

}