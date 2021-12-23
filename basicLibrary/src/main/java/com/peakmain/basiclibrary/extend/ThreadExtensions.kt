package com.peakmain.basiclibrary.extend

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

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
fun <T>T.ktxRunOnUiThread(block:T.()->Unit){
    mHandler.post{
        block()
    }
}

/**
 * 延迟delayMills切换到主线程
 */
fun <T>T.ktxRunOnUiThreadDelay(delayMills:Long,block: T.() -> Unit){
    mHandler.postDelayed({
        block()
    },delayMills)
}

/**
 * 子线程执行
 */
fun <T>T.ktxRunOnThreadSingle(block: T.() -> Unit){
    mSingleService.execute{
        block()
    }
}