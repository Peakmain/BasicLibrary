package com.peakmain.basiclibrary.image

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.peakmain.basiclibrary.config.ImageRequestConfig
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.constants.ImageSelectConstants
import com.peakmain.basiclibrary.extend.launchImage
import com.peakmain.basiclibrary.image.contract.SelectMultipleContract
import com.peakmain.basiclibrary.image.contract.SelectSinglePhotoContract
import com.peakmain.basiclibrary.image.contract.TakePictureContract
import com.peakmain.basiclibrary.interfaces.OnImageSelectorCallback
import com.peakmain.basiclibrary.viewmodel.ImageSelectViewModel

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
internal class ImageSelectorFragment : Fragment() {

    private var mImageSelectViewModel: ImageSelectViewModel = ImageSelectViewModel()
    var selectMultipleContract = SelectMultipleContract()
    private val mSelectPhotoLauncher =
        registerForActivityResult(SelectSinglePhotoContract()) { uri ->
            mImageSelectViewModel.registerSingleLauncher(uri)
        }


    private val takePictureLauncher =
        registerForActivityResult(TakePictureContract()) {
            mImageSelectViewModel.registerTakePicture(it)
        }

    private val mSelectMultiPhotoLauncher =
        registerForActivityResult(selectMultipleContract) { lists ->
            mImageSelectViewModel.registerMultiLauncher(lists)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImageSelectViewModel.mStart.observe(
            this,
            mImageSelectViewModel.imageSelectorObserver( {
                mSelectPhotoLauncher.launchImage(mImageSelectViewModel.mConfig)
            }, {
                mSelectMultiPhotoLauncher.launchImage(mImageSelectViewModel.mConfig)
            }) {
                takePictureLauncher.launch(null)
            }
        )
        selectMultipleContract.maxNum = mImageSelectViewModel.mConfig?.maxNum ?: 9
    }


    fun start(onImageSelectorCallback: OnImageSelectorCallback?) {
        getConfig()
        mImageSelectViewModel.mStart.value = true
        mImageSelectViewModel.mOnImageSelectorCallback = onImageSelectorCallback
    }

    private fun getConfig() {
        mImageSelectViewModel.mConfig = if (AndroidVersion.isAndroid13()) {
            arguments?.getSerializable(
                ImageSelectConstants.REQUEST_CONFIG,
                ImageRequestConfig::class.java
            )
        } else {
            arguments?.get(ImageSelectConstants.REQUEST_CONFIG) as ImageRequestConfig?
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mImageSelectViewModel.clear()
    }
}
