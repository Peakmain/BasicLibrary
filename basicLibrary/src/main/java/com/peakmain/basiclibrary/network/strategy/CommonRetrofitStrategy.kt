package com.peakmain.basiclibrary.network.strategy

import com.peakmain.basiclibrary.helper.RetrofitHelper.buildOkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * author ：Peakmain
 * createTime：2022/08/02
 * mail:2726449200@qq.com
 * describe：
 */
class CommonRetrofitStrategy : IRetrofitStrategy {
    override fun <T> createService(service: Class<T>, baseUrl: String): T {
        val retrofit = retrofit2.Retrofit.Builder().baseUrl(baseUrl)
            .client(buildOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(service)
    }

}