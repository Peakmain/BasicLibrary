package com.peakmain.basiclibrary.image

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.peakmain.basiclibrary.config.ImageRequestConfig
import com.peakmain.basiclibrary.constants.ImageSelectConstants
import com.peakmain.basiclibrary.image.contract.SelectSinglePhotoContract
import com.peakmain.basiclibrary.extend.launchSingleImage
/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
internal class ImageSelectorFragment : Fragment() {
    private val mSelectPhotoLauncher =
        registerForActivityResult(SelectSinglePhotoContract()) { uri ->
            Log.e("TAG", "uri:$uri")
        }
    private val mConfig by lazy {
        arguments?.get(ImageSelectConstants.REQUEST_CONFIG) as ImageRequestConfig?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mConfig?.apply {
            if( isSingle){
                mSelectPhotoLauncher.launchSingleImage(mConfig)
            }
        }
    }
}
