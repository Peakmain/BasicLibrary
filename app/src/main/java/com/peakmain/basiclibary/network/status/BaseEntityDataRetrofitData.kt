package com.peakmain.basiclibary.network.status

import com.peakmain.basiclibrary.extend.ktxRunOnUiThread
import com.peakmain.basiclibrary.network.entity.BaseEntity
import com.peakmain.basiclibrary.network.error.ErrorEnum
import com.peakmain.basiclibrary.network.status.AbstractRetrofitData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * author ：Peakmain
 * createTime：2022/08/03
 * mail:2726449200@qq.com
 * describe：
 */
class BaseEntityDataRetrofitData<T>(val apiStatus: ApiBaseStatus<BaseEntity<T>>) :
    AbstractRetrofitData<BaseEntity<T>>(apiStatus) {
    override fun createData(observable: Observable<BaseEntity<T>>): Disposable {
        apiStatus.before()
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ t ->
                checkResult(observable, t, apiStatus)
            }, { exception ->
                checkError(exception, apiStatus)
            })
    }

    private fun checkError(exception: Throwable, apiStatus: ApiBaseStatus<BaseEntity<T>>) {
        exception.printStackTrace()
        apiStatus.error(Exception(exception))
    }

    private fun checkResult(
        observable: Observable<BaseEntity<T>>,
        t: BaseEntity<T>,
        apiStatus: ApiBaseStatus<BaseEntity<T>>
    ) {
        apiStatus.baseData(t)
        when (t.result) {
            ErrorEnum.SUCCESS -> {
                if (t.data != null) {
                    apiStatus.success(t)
                } else {
                    apiStatus.isEmpty()
                }
            }
            ErrorEnum.TOKEN_ERROR -> {
                //token失效
                apiStatus.tokenError(observable, apiStatus)
            }
            else -> {
                apiStatus.ktxRunOnUiThread {
                    error(Exception(t.detail))
                }
            }

        }
    }

}