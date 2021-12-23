package com.peakmain.basiclibrary.network.status

import com.peakmain.basiclibrary.network.entity.BaseEntity
import io.reactivex.Observable

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：
 */
abstract class ApiBaseStatus<T>:BaseApiStatus<T>{
    abstract fun baseData(entity: BaseEntity<T>)

    fun <T> tokenError(observable: Observable<BaseEntity<T>>?, apiStatus: ApiBaseStatus<T>) {

    }
}