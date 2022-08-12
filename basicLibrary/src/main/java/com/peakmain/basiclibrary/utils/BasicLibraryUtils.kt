package com.peakmain.basiclibrary.utils

import android.app.Application
import com.peakmain.basiclibrary.config.BasicLibraryConfig

/**
 * author ：Peakmain
 * createTime：2022/08/12
 * mail:2726449200@qq.com
 * describe：
 */
internal object BasicLibraryUtils {
    /**
     * 获取全局上下文
     */
    @JvmStatic
    val application: Application?
        get() {
           return BasicLibraryConfig.getInstance()?.getApp()?.getApplication()
        }

}