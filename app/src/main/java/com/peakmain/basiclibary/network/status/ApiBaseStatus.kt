package com.peakmain.basiclibary.network.status

import com.peakmain.basiclibrary.network.entity.BaseEntity
import com.peakmain.basiclibrary.network.status.BaseApiStatus
import io.reactivex.Observable

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：
 */
abstract class ApiBaseStatus<T>: BaseApiStatus<T> {
    abstract fun baseData(entity: T)

    fun <T> tokenError(observable: Observable<BaseEntity<T>>?, apiStatus: ApiBaseStatus<BaseEntity<T>>) {

    }
}