package com.peakmain.basiclibary.network;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/13 0013 下午 3:59
 * mail : 2726449200@qq.com
 * describe ：
 */
public interface WanAndroidApi {
    /**
     * https://www.wanandroid.com/project/tree/json
     */
    @GET("project/tree/json")
    Observable<DataResponse<ProjectTree>> getProjectTree();
}
