package com.peakmain.basiclibrary.base

/**
 * author ：Peakmain
 * createTime：2022/4/22
 * mail:2726449200@qq.com
 * describe：
 */

abstract class BaseEmptySingleton<out T> {
    @Volatile
    private var sInstance: T? = null
    protected abstract val createSingleton :()->T
    fun getInstance(): T? {
        sInstance ?: synchronized(this) {
            sInstance ?: createSingleton().also {
                sInstance = it
            }
        }
        return sInstance
    }
}

abstract class BaseOneSingleton<in P, out T> {
    @Volatile
    private var sInstance: T? = null
    protected abstract fun createSingleton(params:P):T
    fun getInstance(params: P): T? {
        sInstance ?: synchronized(this) {
            sInstance ?: createSingleton(params).also {
                sInstance = it
            }
        }
        return sInstance
    }
}

abstract class BaseTwoSingleton<in P1, in P2, out T> {
    @Volatile
    private var sInstance: T? = null
    protected abstract fun createSingleton(params1: P1, params2: P2): T?
    fun getInstance(params1: P1, params2: P2): T? {
        sInstance ?: synchronized(this) {
            sInstance ?: createSingleton(params1, params2).also {
                sInstance = it
            }
        }
        return sInstance
    }
}