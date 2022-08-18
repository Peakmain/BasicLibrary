package com.peakmain.basiclibrary.viewmodel

import androidx.lifecycle.MutableLiveData
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
class ImageSelectViewModel : BaseViewModel() {
    var mStart = MutableLiveData<Boolean>(false)
    override fun initModel() {

    }
}