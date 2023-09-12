package com.peakmain.basiclibrary.extend

/**
 * author ：Peakmain
 * createTime：2023/9/8
 * mail:2726449200@qq.com
 * describe：检测工具
 */
fun <T> List<T>?.indexOfBound(index: Int): Boolean {
    if (this.isNullOrEmpty()) return true
    return index >= this.size
}
