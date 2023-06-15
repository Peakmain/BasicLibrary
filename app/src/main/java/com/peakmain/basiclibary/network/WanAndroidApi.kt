package com.peakmain.basiclibary.network

import com.peakmain.basiclibrary.network.entity.BaseEntity
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/13 0013 下午 3:59
 * mail : 2726449200@qq.com
 * describe ：
 */
interface WanAndroidApi {
    /**
     * https://www.wanandroid.com/project/tree/json
     */
    @get:GET("project/tree/json")
    val projectTree: Observable<DataResponse<ProjectTree>>

    @get:GET("project/tree/json")
    val projectTree1: Observable<BaseEntity<ProjectTree>>

    @get:GET("project/tree/json")
    val projectTree2: Flow<DataResponse<ProjectTree>>
}