package com.peakmain.basiclibrary.base.viewmodel

import androidx.lifecycle.ViewModel

/**
 * author ：Peakmain
 * createTime：2021/12/24
 * mail:2726449200@qq.com
 * describe：
 */
abstract class BaseViewModel: ViewModel() {
    abstract fun initModel()
}