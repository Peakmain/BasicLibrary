package com.peakmain.basiclibrary.interfaces

import android.net.Uri

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
interface OnImageSelectorCallback {
    fun onImageSelect(uris:List<Uri?>)
}