package com.peakmain.basiclibrary.config

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.peakmain.basiclibrary.constants.ImageSelectConstants
import com.peakmain.basiclibrary.interfaces.OnImageSelectorCallback
import java.io.Serializable

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
class ImageRequestConfig : Serializable {
    //默认是单选
    var isSingle: Boolean = true

    //默认是图片类型
    var imageType: Int = ImageSelectConstants.IMAGE_TYPE
}

internal data class ImageContext(
    var fragment: Fragment? = null,
    var activity: FragmentActivity? = null,
    var onImageSelectorCallback: OnImageSelectorCallback?=null
)