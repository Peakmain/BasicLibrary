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
class CommonRetrofitData<T>(private val apiStatus: ApiStatus<T>) :
    AbstractRetrofitData<T>(apiStatus) {

    override fun createData(observable: Observable<T>): Disposable {
        apiStatus.before()
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ t ->
                apiStatus.success(t)
            }, { exception ->
                exception.printStackTrace()
                apiStatus.error(Exception(exception))
            })
    }
}