package com.peakmain.basiclibrary.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.os.Build
import android.text.TextUtils
import com.peakmain.basiclibrary.utils.reflect.ReflectUtils
import com.peakmain.ui.utils.LogUtils
import java.lang.reflect.Method

/**
 * author ：Peakmain
 * createTime：2022/3/1
 * mail:2726449200@qq.com
 * describe：
 */
object WebViewHelper {
    const val LIBRARY_WEB_VIEW = "LIBRARY_WEB_VIEW"
    const val LIBRARY_WEB_VIEW_URL = "LIBRARY_WEB_VIEW_URL"
    private var isFirst = false
    fun loadWebViewResource(context: Context): Boolean {
        if (isFirst) return true
        val dir: String =
            getWebViewResourceDir(context)
        if (TextUtils.isEmpty(dir)) {
            return false
        }

        try {
            val m = getAddAssetPathMethod()
            if (m != null) {
                val ret = m.invoke(context.assets, dir) as Int
                isFirst = ret > 0
                return isFirst
            }
        } catch (e: Exception) {
            LogUtils.e("loadWebViewResource is error,method error:$e")
        }

        return false
    }

    private fun getAddAssetPathMethod(): Method? {
        var m: Method? = null
        val c: Class<*> = AssetManager::class.java
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                m = c.getDeclaredMethod("addAssetPathAsSharedLibrary", String::class.java)
                m.isAccessible = true
            } catch (e: NoSuchMethodException) {
                LogUtils.e(e.localizedMessage)
            }
            return m
        }
        try {
            m = c.getDeclaredMethod("addAssetPath", String::class.java)
            m.isAccessible = true
        } catch (e: NoSuchMethodException) {
            LogUtils.e(e.localizedMessage)
        }
        return m
    }

    fun getWebViewResourceDir(context: Context): String {
        val pkgName: String =
            getWebViewPackageName()
        if (TextUtils.isEmpty(pkgName)) {
            return ""
        }

        try {
            val pi: PackageInfo = context.packageManager.getPackageInfo(
                getWebViewPackageName(),
                PackageManager.GET_SHARED_LIBRARY_FILES
            )
            return pi.applicationInfo.sourceDir
        } catch (e: PackageManager.NameNotFoundException) {
            LogUtils.e("get webView application info failed! $e")
        }
        return ""
    }

    fun getWebViewPackageName(): String {
        val sdkInt = Build.VERSION.SDK_INT
        if (sdkInt <= 20) {
            return ""
        }
        return when (sdkInt) {
            21, 22, 23 -> {
                getWebViewPackageName21()
            }
            else -> {
                getWebViewPackageName24()
            }

        }
    }

    @SuppressLint("PrivateApi")
    private fun getWebViewPackageName24(): String {
        try {
            val c = ReflectUtils.invokeStaticMethod(
                "android.webkit.WebViewFactory",
                "getWebViewContextAndSetProvider",
                null
            ) as Context
            return c.applicationInfo.packageName
        } catch (e: Throwable) {
            LogUtils.e(e.message)
        }
        return "com.google.android.webview"
    }

    @SuppressLint("PrivateApi")
    private fun getWebViewPackageName21(): String {
        try {
            return ReflectUtils.invokeStaticMethod(
                "android.webkit.WebViewFactory",
                "getWebViewPackageName",
                null
            ) as String
        } catch (e: Throwable) {
            LogUtils.e(e.message)
        }
        return "com.google.android.webview"
    }
}