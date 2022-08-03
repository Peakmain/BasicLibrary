package com.peakmain.basiclibrary.network

import com.peakmain.basiclibrary.network.status.AbstractRetrofitData
import com.peakmain.basiclibrary.network.status.ApiStatus
import com.peakmain.basiclibrary.network.status.CommonRetrofitData
import com.peakmain.basiclibrary.network.strategy.CommonRetrofitStrategy
import com.peakmain.basiclibrary.network.strategy.IRetrofitStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：Retrofit的管理类
 */
class RetrofitManager {
    companion object {
        private var mStrategy: IRetrofitStrategy = CommonRetrofitStrategy()
        fun executeStrategy(strategy: IRetrofitStrategy) {
            this.mStrategy = strategy
        }

        fun <T> createService(service: Class<T>, block: (service: Class<T>) -> T): T {
            return block(service)
        }

        fun <T> createService(service: Class<T>, baseUrl: String): T {
            return mStrategy.createService(service, baseUrl)
        }

        fun <T> createData(
            observable: Observable<T>,
            retrofitData: AbstractRetrofitData<T>
        ): Disposable {
            return retrofitData.createData(observable)
        }

        fun <T> createData(
            observable: Observable<T>,
            apiStatus: ApiStatus<T>,
            retrofitData: AbstractRetrofitData<T> = CommonRetrofitData(apiStatus)
        ): Disposable {
            return retrofitData.createData(observable)
        }
        fun <T> createData(
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
}