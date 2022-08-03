package com.peakmain.basiclibrary.network.strategy

import android.os.Build
import com.peakmain.basiclibrary.network.MyX509
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * author ：Peakmain
 * createTime：2022/08/02
 * mail:2726449200@qq.com
 * describe：
 */
class CommonRetrofitStrategy : IRetrofitStrategy {
    companion object{
        //连接超时
        private const val CONNECT_TIMEOUT = 60L

        //阅读超时
        private const val READ_TIMEOUT = 10L

        //写入超时
        private const val WRITE_TIMEOUT = 10L
        fun buildOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//读取超时
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//写入超时
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .hostnameVerifier { _, _ -> true }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val x509 = MyX509()
                builder.sslSocketFactory(getSSLFactory(x509), x509)
            }
          prevBuildOkHttpClient(builder)
            return builder.build()
        }
        private fun getSSLFactory(x509TrustManager: X509ExtendedTrustManager): SSLSocketFactory {
            val trustAllCerts = arrayOf<TrustManager>(x509TrustManager)
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            return sslContext.socketFactory
        }

        /**
         * 空方法，在构建okHttpClient之前可设置一些参数
         */
        fun prevBuildOkHttpClient(builder: OkHttpClient.Builder) {

        }
    }
    override fun <T> createService(service: Class<T>, baseUrl: String): T {
        val retrofit = retrofit2.Retrofit.Builder().baseUrl(baseUrl)
            .client(buildOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(service)
    }

}