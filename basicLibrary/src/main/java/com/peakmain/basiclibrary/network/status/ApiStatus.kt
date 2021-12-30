package com.peakmain.basiclibrary.network.status

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：
 */
abstract class ApiStatus<T>:BaseApiStatus<T>{
    override fun before() {
    }
    override fun isEmpty() {
    }

    override fun loadMore(t: T, isRefresh: Boolean) {
    }
}