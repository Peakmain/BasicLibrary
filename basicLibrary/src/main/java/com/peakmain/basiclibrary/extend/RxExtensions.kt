package com.peakmain.basiclibrary.extend

import io.reactivex.disposables.Disposable

/**
 * author ：Peakmain
 * createTime：2022/10/24
 * mail:2726449200@qq.com
 * describe：Rx 扩展工具类
 */
/**
 * Rxjava Disposable取消
 */
fun Disposable?.cancel() {
    if (this == null) return
    if (!this.isDisposed) this.dispose()
}