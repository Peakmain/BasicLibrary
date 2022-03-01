package com.peakmain.basiclibrary.utils

import android.text.TextUtils

/**
 * author ：Peakmain
 * createTime：2022/3/1
 * mail:2726449200@qq.com
 * describe：
 */
object LibraryUtils {
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
}