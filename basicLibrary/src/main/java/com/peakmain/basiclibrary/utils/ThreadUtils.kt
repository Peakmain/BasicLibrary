package com.peakmain.basiclibrary.utils

import android.os.Handler
import android.os.Looper
import com.peakmain.basiclibrary.config.BasicLibraryConfig
import com.peakmain.basiclibrary.constants.AndroidVersion
import java.util.concurrent.Executor
import java.util.concurrent.RejectedExecutionException

/**
 * author ：Peakmain
 * createTime：2022/10/31
 * mail:2726449200@qq.com
 * describe：线程工具类
 */
object ThreadUtils {
    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
    }
    private var mExecutor = HandlerExecutor(handler)

    /**
     * 获得主线程的线程池
     */
    fun getMainExecutor(): Executor? {
        val application = BasicLibraryConfig.getInstance()?.getApp()?.getApplication()
        return if (AndroidVersion.isAndroid9())
            application?.mainExecutor
        else
            mExecutor
    }

    class HandlerExecutor(private val mHandler: Handler) : Executor {
        override fun execute(command: Runnable?) {
            if (command != null && !mHandler.post(command)) {
                throw RejectedExecutionException("$mHandler is shutting down")
            }
        }

    }

    fun assertMainThread(methodName: String) {
        check(!isMainThread()) {
            ("Cannot invoke " + methodName + " on a background"
                    + " thread")
        }
    }

    fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }
}
