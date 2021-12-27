package com.peakmain.basiclibrary.base

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

/**
 * author ：Peakmain
 * createTime：2021/12/24
 * mail:2726449200@qq.com
 * describe：
 */
interface IApp {
    fun getViewModelProvider(): ViewModelProvider {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        return ViewModelProvider(getViewModelStore(), factory)
    }

    fun getApplication(): Application
    fun getViewModelStore(): ViewModelStore
}