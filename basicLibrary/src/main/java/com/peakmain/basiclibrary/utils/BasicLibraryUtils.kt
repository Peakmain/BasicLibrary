package com.peakmain.basiclibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.TextUtils

/**
 * author ：Peakmain
 * createTime：2022/3/1
 * mail:2726449200@qq.com
 * describe：
 */
object BasicLibraryUtils {
    /**
     * 为url加上token
     *
     * @param url 原来的url
     * @return 如果没有token就添加token，有就不添加
     */
    fun addTokenToUrl(url: String, token: String): String {
        if (TextUtils.isEmpty(url)) return url
        var tokenUrl = url
        if (!url.contains("?")) {
            tokenUrl = "$url?token=$token"
        } else {
            if (!url.contains("token=") && !url.contains("needToken=1")) {
                tokenUrl = "$url&token=$token"
            } else if (!url.contains("token=") && url.contains("needToken=1")) {
                tokenUrl = "$url&token=$token"
            }
        }
        return tokenUrl
    }

    /**
     * 判断当前网络是否可用
     */
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context != null) {
            val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val info = connectivity.activeNetworkInfo
                if (info != null && info.isConnected) {
                    // 当前网络是连接的
                    if (info.state == NetworkInfo.State.CONNECTED) {
                        // 当前所连接的网络可用
                        return true
                    }
                }
            }
        }
        return false
    }
}