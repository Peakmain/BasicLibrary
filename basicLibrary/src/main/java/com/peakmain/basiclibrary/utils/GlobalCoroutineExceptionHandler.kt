package com.peakmain.basiclibrary.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * author ：Peakmain
 * createTime：2022/2/15
 * mail:2726449200@qq.com
 * describe：
 */
class GlobalCoroutineExceptionHandler : CoroutineExceptionHandler {
    override val key = CoroutineExceptionHandler

    var coroutineExceptionCallback: ((context: CoroutineContext, exception: Throwable) -> Unit)? =
        null

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        coroutineExceptionCallback?.let {
            it(context, exception)
        }
    }

}