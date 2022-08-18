package com.peakmain.basiclibrary.constants

import androidx.annotation.IntDef

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
object ImageSelectConstants {
    const val REQUEST_CONFIG: String = "requestConfig"
    const val TAKE_PHOTO_TYPE = 0
    const val IMAGE_TYPE = 1
    const val VIDEO_TYPE = 2
    const val ALL_TYPE = 3

    @IntDef(TAKE_PHOTO_TYPE, IMAGE_TYPE, VIDEO_TYPE, ALL_TYPE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ImageSelectType


}