package com.peakmain.basiclibrary.image

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.peakmain.basiclibrary.config.ImageRequestConfig
import com.peakmain.basiclibrary.constants.ImageSelectConstants
import com.peakmain.basiclibrary.extend.launchImage
import com.peakmain.basiclibrary.image.contract.SelectMultipleContract
import com.peakmain.basiclibrary.image.contract.SelectSinglePhotoContract
import com.peakmain.basiclibrary.interfaces.OnImageSelectorCallback
import com.peakmain.basiclibrary.viewmodel.ImageSelectViewModel

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
internal class ImageSelectorFragment : Fragment() {
    private var mOnImageSelectorCallback: OnImageSelectorCallback?=null
    private var mImageSelectViewModel: ImageSelectViewModel = ImageSelectViewModel()
    private val mSelectPhotoLauncher =
        registerForActivityResult(SelectSinglePhotoContract()) { uri ->
            mImageSelectViewModel.mStart.value = false
            mOnImageSelectorCallback?.onImageSelect(arrayListOf(uri))
        }
    private val mSelectMultiPhotoLauncher =
        registerForActivityResult(SelectMultipleContract()) { lists ->
            mImageSelectViewModel.mStart.value = false
            mOnImageSelectorCallback?.onImageSelect(lists)
        }

    private val mConfig by lazy {
        arguments?.get(ImageSelectConstants.REQUEST_CONFIG) as ImageRequestConfig?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImageSelectViewModel.mStart.observe(this, Observer { start ->
            mConfig?.apply {
                if (!start) return@Observer
                if (isSingle) {
                    mSelectPhotoLauncher.launchImage(mConfig)
                } else {
                    mSelectMultiPhotoLauncher.launchImage(mConfig)
                }
            }
        })
    }


    fun start(onImageSelectorCallback: OnImageSelectorCallback?) {
        mImageSelectViewModel.mStart.value = true
        mOnImageSelectorCallback=onImageSelectorCallback
    }
}
