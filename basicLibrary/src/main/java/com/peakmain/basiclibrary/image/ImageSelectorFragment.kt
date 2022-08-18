package com.peakmain.basiclibrary.image

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.peakmain.basiclibrary.config.ImageRequestConfig
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
    private val mSelectPhotoLauncher =
        registerForActivityResult(SelectSinglePhotoContract()) { uri ->
            mImageSelectViewModel.registerSingleLauncher(uri)
        }
    private val mSelectMultiPhotoLauncher =
        registerForActivityResult(SelectMultipleContract()) { lists ->
            mImageSelectViewModel.registerMultiLauncher(lists)
        }

    private val takePictureLauncher =
        registerForActivityResult(TakePictureContract()) {
            mImageSelectViewModel.registerTakePicture(it)
        }
    private val mConfig by lazy {
        arguments?.get(ImageSelectConstants.REQUEST_CONFIG) as ImageRequestConfig?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImageSelectViewModel.mStart.observe(
            this,
            mImageSelectViewModel.imageSelectorObserver(mConfig, {
                mSelectPhotoLauncher.launchImage(mConfig)
            },{
                mSelectMultiPhotoLauncher.launchImage(mConfig)
            }) {
                takePictureLauncher.launch(null)
            }
        )
    }


    fun start(onImageSelectorCallback: OnImageSelectorCallback?) {
        mImageSelectViewModel.mStart.value = true
        mImageSelectViewModel.mOnImageSelectorCallback = onImageSelectorCallback
    }

    override fun onDestroy() {
        super.onDestroy()
        mImageSelectViewModel.clear()
    }
}
