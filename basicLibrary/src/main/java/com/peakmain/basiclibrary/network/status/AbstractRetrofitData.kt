package com.peakmain.basiclibrary.network.status

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * author ：Peakmain
 * createTime：2022/08/02
 * mail:2726449200@qq.com
 * describe：
 */
abstract class AbstractRetrofitData<T>(apiStatus: BaseApiStatus<T>) {
    protected var mBaseApiStatus: BaseApiStatus<T> = apiStatus
    abstract fun createData(
        observable: Observable<T>
    ): Disposable

    open fun createData(
        observable: Observable<T>,
        before: () -> Unit,
        success: T.() -> Unit,
        error: (Exception) -> Unit = {}
    ): Disposable {
        before()
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ t ->
                success(t)
            }, { throwable ->
                error(Exception(throwable))
            })
    }
}