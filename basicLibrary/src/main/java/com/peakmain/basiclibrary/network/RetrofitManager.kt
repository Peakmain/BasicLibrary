package com.peakmain.basiclibrary.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.peakmain.basiclibrary.extend.ktxRunOnUiThread
import com.peakmain.basiclibrary.network.entity.BaseEntity
import com.peakmain.basiclibrary.network.error.ErrorEnum
import com.peakmain.basiclibrary.network.status.ApiBaseStatus
import com.peakmain.basiclibrary.network.status.ApiStatus
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509ExtendedTrustManager

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：Retrofit的管理类
 */
class RetrofitManager {
    companion object {
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
                .hostnameVerifier(HostnameVerifier { _, _ -> true })
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val x509 = MyX509()
                builder.sslSocketFactory(getSSLFactory(x509), x509)
            }
            prevBuildOkHttpClient(builder)
            return builder.build()
        }

        /**
         * 空方法，在构建okHttpClient之前可设置一些参数
         */
        fun prevBuildOkHttpClient(builder: OkHttpClient.Builder) {

        }

        private fun getSSLFactory(x509TrustManager: X509ExtendedTrustManager): SSLSocketFactory {
            val trustAllCerts = arrayOf<TrustManager>(x509TrustManager)
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            return sslContext.socketFactory
        }

        fun <T> createService(service: Class<T>, baseUrl: String): T {
            val retrofit = retrofit2.Retrofit.Builder().baseUrl(baseUrl).client(buildOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            return retrofit.create(service)
        }

        fun <T> createData(observable: Observable<T>, apiStatus: ApiStatus<T>): Disposable {
            apiStatus.before()
            return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ t ->
                    apiStatus.baseData(t)
                }, { exception ->
                    exception.printStackTrace()
                    apiStatus.error(Exception(exception))
                })
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

        fun <T> createBaseEntityData(
            observable: Observable<BaseEntity<T>>,
            apiStatus: ApiBaseStatus<T>
        ): Disposable {
            apiStatus.before()
            return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ t ->
                    checkResult(observable, t, apiStatus)
                }, { exception ->
                    checkError(exception, apiStatus)
                })
        }

        private fun <T> checkError(exception: Throwable, apiStatus: ApiBaseStatus<T>) {
            exception.printStackTrace()
            apiStatus.error(Exception(exception))
        }

        private fun <T> checkResult(
            observable: Observable<BaseEntity<T>>,
            t: BaseEntity<T>,
            apiStatus: ApiBaseStatus<T>
        ) {
            apiStatus.baseData(t)
            when (t.result) {
                ErrorEnum.SUCCESS.code -> {
                    if (t.data != null) {
                        apiStatus.success(t.data)
                    } else {
                        apiStatus.isEmpty()
                    }
                }
                ErrorEnum.TOKEN_ERROR.code -> {
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
}