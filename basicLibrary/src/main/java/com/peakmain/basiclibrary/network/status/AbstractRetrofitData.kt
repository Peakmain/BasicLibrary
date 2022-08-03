package com.peakmain.basiclibrary.network.status

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * author ：Peakmain
 * createTime：2022/08/02
 * mail:2726449200@qq.com
 * describe：
 */
abstract class AbstractRetrofitData<T>(apiStatus: BaseApiStatus<T>) {
    protected var mBaseApiStatus: BaseApiStatus<T> = apiStatus
    public abstract fun createData(
        observable: Observable<T>
    ): Disposable
}