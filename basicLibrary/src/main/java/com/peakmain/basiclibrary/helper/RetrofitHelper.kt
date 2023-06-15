package com.peakmain.basiclibrary.helper

import android.os.Build
import com.peakmain.basiclibrary.network.HttpDns
import com.peakmain.basiclibrary.network.MyX509
import com.peakmain.basiclibrary.network.status.ApiStatus
import com.peakmain.basiclibrary.network.status.CommonRetrofitData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509ExtendedTrustManager

/**
 * author ：Peakmain
 * createTime：2022/08/04
 * mail:2726449200@qq.com
 * describe：
 */
object RetrofitHelper {
    //连接超时
    private const val CONNECT_TIMEOUT = 60L

    //阅读超时
    private const val READ_TIMEOUT = 10L

    //写入超时
    private const val WRITE_TIMEOUT = 10L

    /**
     * @param prevBuildOkHttpClient 在构建okHttpClient之前可设置一些参数
     */
    fun buildOkHttpClient(prevBuildOkHttpClient: ((OkHttpClient.Builder) -> Unit)? = null): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时
            .dns(HttpDns())
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//读取超时
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//写入超时
        builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .hostnameVerifier { _, _ -> true }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val x509 = MyX509()
            builder.sslSocketFactory(getSSLFactory(x509), x509)
        }
        prevBuildOkHttpClient?.invoke(builder)
        return builder.build()
    }

    private fun getSSLFactory(x509TrustManager: X509ExtendedTrustManager): SSLSocketFactory {
        val trustAllCerts = arrayOf<TrustManager>(x509TrustManager)
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        return sslContext.socketFactory
    }


    fun <T> function2RetrofitData(
        before: () -> Unit,
        success: T.() -> Unit,
        error: (Exception) -> Unit
    ): CommonRetrofitData<T> {
        return CommonRetrofitData(object : ApiStatus<T>() {
            override fun before() {
                super.before()
                before()
            }

            override fun success(t: T) {
                success(t)
            }

            override fun error(exception: Exception) {
                error(exception)
            }

        })
    }


}