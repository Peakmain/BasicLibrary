package com.peakmain.basiclibrary.extend

import android.os.Handler
import android.os.Looper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：线程扩展类
 */
private val mHandler = Handler(Looper.getMainLooper())

private val mSingleService: ExecutorService = Executors.newSingleThreadExecutor()

/**
 * 切换到主线程
 */
fun <T> T.ktxRunOnUiThread(block: T.() -> Unit) {
    mHandler.post {
        block()
    }
}

/**
 * 延迟delayMills切换到主线程,默认是600ms
 */
fun <T> T.ktxRunOnUiThreadDelay(delayMills: Long=600, block: T.() -> Unit) {
    mHandler.postDelayed({
        block()
    }, delayMills)
}

/**
 * 子线程执行
 */
fun <T> T.ktxRunOnThreadSingle(block: T.() -> Unit) {
    mSingleService.execute {
        block()
    }
}

/**
 * 延迟加载
 */
fun <T> T.wait(delay: Long = 500L, block: T.() -> Unit): Disposable {
    return wait(delay, TimeUnit.MILLISECONDS, block)
}
/**
 * 延迟加载
 */
fun <T> T.wait(delay: Long = 500L, unit: TimeUnit, block: T.() -> Unit): Disposable {
    return Observable.timer(delay, unit).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe { block() }
}