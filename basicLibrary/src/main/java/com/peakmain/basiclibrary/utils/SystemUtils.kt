package com.peakmain.basiclibrary.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
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
    fun getSdkVersion(): Int = android.os.Build.VERSION.SDK_INT

    /**
     * 获取手机系统版本号
     *
     * @return 形如2.3.3
     */
    fun getDeviceSystemVersion(): String = android.os.Build.VERSION.RELEASE

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    fun getDeviceModel(): String = android.os.Build.MODEL

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    fun getDeviceBrand(): String = Build.BRAND

    /**
     * 返回当前程序版本名
     */
    fun getAppVersionName(context: Context): String? {
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
     * 国际移动用户识别码
     */
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
        manager.runningAppProcesses.forEach {
            if (it.pid == android.os.Process.myPid()) {
                return it.processName
            }
        }
        return null
    }
}