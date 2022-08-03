package com.peakmain.basiclibrary.network.strategy

/**
 * author ：Peakmain
 * createTime：2022/08/02
 * mail:2726449200@qq.com
 * describe：
 */
interface IRetrofitStrategy {
    fun <T> createService(service: Class<T>, baseUrl: String): T
}