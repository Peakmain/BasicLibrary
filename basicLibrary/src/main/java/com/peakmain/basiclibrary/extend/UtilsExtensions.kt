package com.peakmain.basiclibrary.extend

import com.google.gson.Gson

/**
 * author ：Peakmain
 * createTime：2021/12/28
 * mail:2726449200@qq.com
 * describe：
 */
inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson(json, T::class.java)
}