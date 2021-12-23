package com.peakmain.basiclibrary.network.status

import java.lang.Exception

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：
 */
interface BaseApiStatus<T> {
    fun before()

    fun success(t: T)

    fun isEmpty()

    fun loadMore(t: T, isRefresh: Boolean)

    fun error(exception: Exception)
}