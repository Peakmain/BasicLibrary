package com.peakmain.basiclibrary.permission.interfaces

/**
 * author ：Peakmain
 * createTime：2022/10/31
 * mail:2726449200@qq.com
 * describe：
 */
interface ICall: Cloneable {
    fun call()

    override fun clone():ICall
}