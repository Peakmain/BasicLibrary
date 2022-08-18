package com.peakmain.basiclibrary.helper

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.peakmain.basiclibrary.config.ImageContext
import com.peakmain.basiclibrary.image.ImageSelector
import com.peakmain.basiclibrary.image.ImageSelectorFragment
import com.peakmain.basiclibrary.config.ImageRequestConfig
import com.peakmain.basiclibrary.constants.ImageSelectConstants

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
internal class ImageSelectorHelper private constructor() {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ImageSelectorHelper()
        }
        private val TAG = ImageSelector::class.simpleName
    }

    fun getPictureSelectFragment(
        config: ImageRequestConfig,
        imageContext: ImageContext
    ): ImageSelectorFragment {
        val fragmentManager = getFragmentManager(imageContext)
        var imageSelectorFragment = findPictureSelectFragment(fragmentManager)
        if (imageSelectorFragment == null) {
            imageSelectorFragment = ImageSelectorFragment()
            val bundle = Bundle()
            bundle.putSerializable(ImageSelectConstants.REQUEST_CONFIG, config)
            imageSelectorFragment.arguments = bundle
            fragmentManager?.beginTransaction()
                ?.add(imageSelectorFragment, TAG)
                ?.commitAllowingStateLoss()
        }
        return imageSelectorFragment
    }

    private fun findPictureSelectFragment(fragmentManager: FragmentManager?): ImageSelectorFragment? {
        return fragmentManager?.findFragmentByTag(TAG) as ImageSelectorFragment?
    }


    private fun getFragmentManager(imageContext: ImageContext): FragmentManager? {
        return if (imageContext.activity != null) {
            imageContext.activity!!.supportFragmentManager
        } else if (imageContext.fragment != null) {
            imageContext.fragment!!.childFragmentManager
        } else {
            null
        }
    }


}