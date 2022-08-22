package com.peakmain.basiclibrary.image

import android.graphics.Bitmap
import com.peakmain.basiclibrary.interfaces.OnImageSelectorCallback

/**
 * author ：Peakmain
 * createTime：2022/08/22
 * mail:2726449200@qq.com
 * describe：接口适配器设计模式
 */
abstract class SimpleImageSelectorCallback : OnImageSelectorCallback {
    //拍照的空方法
    override fun onImageSelect(bitmap: Bitmap?) {

    }

}