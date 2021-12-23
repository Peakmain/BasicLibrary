package com.peakmain.basiclibary

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
}