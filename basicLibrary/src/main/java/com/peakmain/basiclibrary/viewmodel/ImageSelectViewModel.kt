package com.peakmain.basiclibrary.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.basiclibrary.config.ImageRequestConfig
import com.peakmain.basiclibrary.interfaces.OnImageSelectorCallback

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
internal class ImageSelectViewModel : BaseViewModel() {
    var mStart = MutableLiveData(false)
    var mOnImageSelectorCallback: OnImageSelectorCallback? = null
    var mConfig: ImageRequestConfig? = null
    override fun initModel() {

    }

    fun clearImage() {
        mStart.value = null
        mOnImageSelectorCallback = null
        mConfig = null
    }

    fun registerSingleLauncher(uri: Uri?) {
        mStart.value = false
        mOnImageSelectorCallback?.onImageSelect(arrayListOf(uri))
    }

    fun registerMultiLauncher(lists: List<Uri?>) {
        mStart.value = false
        mOnImageSelectorCallback?.onImageSelect(lists)
    }

    fun registerTakePicture(pair: Pair<Boolean, Bitmap?>) {
        if (pair.first) {
            val bitmap = pair.second
            mOnImageSelectorCallback?.onImageSelect(bitmap)
        }
        mOnImageSelectorCallback = null
    }

    fun imageSelectorObserver(
        selectPhotoLauncher: ((ImageRequestConfig) -> Unit)? = null,
        selectMultiPhotoLauncher: ((ImageRequestConfig) -> Unit)? = null,
        takePictureLauncher: (() -> Unit)? = null
    )
            : Observer<Boolean> = Observer { start ->
        mConfig?.also {
            if (!start) return@Observer
            if (it.imageType != 0 && it.isSingle) {
                selectPhotoLauncher?.invoke(it)
            } else if (it.imageType == 0) {
                takePictureLauncher?.invoke()
            } else {
                selectMultiPhotoLauncher?.invoke(it)
            }
        }
    }
}