package com.peakmain.basiclibrary.network

import com.peakmain.basiclibrary.network.status.AbstractRetrofitData
import com.peakmain.basiclibrary.network.status.ApiStatus
import com.peakmain.basiclibrary.network.status.CommonRetrofitData
import com.peakmain.basiclibrary.network.strategy.CommonRetrofitStrategy
import com.peakmain.basiclibrary.network.strategy.IRetrofitStrategy
import com.peakmain.basiclibrary.helper.RetrofitHelper
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

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
            error: (Exception) -> Unit = {},
            retrofitData: AbstractRetrofitData<T>? = null
        ): Disposable {
            if (retrofitData == null) {
                val tempRetrofitData = RetrofitHelper.function2RetrofitData(before, success, error)
                return tempRetrofitData.createData(observable)
            }
            return retrofitData.createData(observable)
        }


    }
}