package com.peakmain.basiclibrary.extend

import android.content.Context
import androidx.fragment.app.FragmentActivity

/**
 * author ：Peakmain
 * createTime：2024/11/14
 * mail:2726449200@qq.com
 * describe：空逻辑处理
 */
fun Context?.handleEmpty(block: ((Context) -> Unit)? = null) {
    this?.let {
        block?.invoke(it)
    }
}
fun FragmentActivity?.handleEmptyActivity(block: ((FragmentActivity) -> Unit)? = null) {
    this?.let {
        block?.invoke(it)
    }
}