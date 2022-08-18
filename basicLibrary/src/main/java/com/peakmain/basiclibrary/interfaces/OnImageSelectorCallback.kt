package com.peakmain.basiclibrary.interfaces

import android.graphics.Bitmap
import android.net.Uri

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
interface OnImageSelectorCallback {
    //图片选择的回调
    fun onImageSelect(uris: List<Uri?>)
    //相机拍照的回调
    fun onImageSelect(bitmap: Bitmap?)
}