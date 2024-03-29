package com.peakmain.basiclibrary.image

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.peakmain.basiclibrary.config.ImageContext
import com.peakmain.basiclibrary.helper.ImageSelectorHelper
import com.peakmain.basiclibrary.config.ImageRequestConfig
import com.peakmain.basiclibrary.constants.ImageSelectConstants
import com.peakmain.basiclibrary.interfaces.OnImageSelectorCallback

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
class PkImageSelector private constructor(mConfig: ImageRequestConfig, imageContext: ImageContext) {
    private var mPictureSelectorFragment: ImageSelectorFragment? = null

    init {
        mPictureSelectorFragment = ImageSelectorHelper.instance
            .getPictureSelectFragment(mConfig, imageContext)
        mPictureSelectorFragment?.start(imageContext.onImageSelectorCallback)
    }

    companion object {

        fun builder(fragment: Fragment): Builder {
            return Builder(fragment)
        }

        fun builder(activity: FragmentActivity): Builder {
            return Builder(activity)
        }

        class Builder private constructor() {
            private val mConfig: ImageRequestConfig = ImageRequestConfig()
            private val imageContext = ImageContext()

            constructor(activity: FragmentActivity) : this() {
                imageContext.activity = activity
            }

            constructor(fragment: Fragment) : this() {
                imageContext.fragment = fragment
            }


            fun setSingle(isSingle: Boolean): Builder {
                mConfig.isSingle = isSingle
                return this
            }

            /**
             * 设置多选最多的数量
             * android 13及以上才生效
             */
            fun setMaxNum(num: Int = 1):Builder {
                mConfig.maxNum = num
                return this
            }

            fun setType(@ImageSelectConstants.ImageSelectType type: Int): Builder {
                mConfig.imageType = type
                return this
            }

            fun forResult(onImageSelectorCallback: OnImageSelectorCallback? = null): PkImageSelector {
                imageContext.onImageSelectorCallback = onImageSelectorCallback
                return PkImageSelector(mConfig, imageContext)
            }

        }
    }
}