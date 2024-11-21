package com.peakmain.basiclibary.utils

import android.app.Activity
import com.peakmain.basiclibrary.interfaces.IPermissionPopupListener
import com.peakmain.basiclibrary.utils.toast.PkToastUtils

/**
 * author ：Peakmain
 * createTime：2024/11/21
 * mail:2726449200@qq.com
 * describe：
 */
class AtPermissionUtils(val activity: Activity?) {

    val locationListener = createRequestListener(
        "位置权限使用说明",
        "开启定位权限，为您提供酒店推荐服务。"
    )

    val photoListener = createRequestListener(
        "相册权限使用说明",
        "开启相册权限，为您提供个人信息头像上传、随手拍、竹居借书、在线客服上传照片与视频、申请差旅合作提供营业执照和联系人证件、上传酒店和零售商品点评照片服务。"
    )
    val cameraListener = createRequestListener(
        "相机权限使用说明",
        "开启相机权限，为您提供个人信息头像上传、随手拍、竹居借书、在线客服上传照片与视频、申请差旅合作提供营业执照和联系人证件、上传酒店和零售商品点评照片服务。"
    )
    val storageListener = createRequestListener(
        "存储权限说明",
        "开启存储权限，帮组您实现更换头像、上传图片、客服沟通服务"
    )
    val blueListener = createRequestListener(
        "蓝牙权限使用说明",
        "开启蓝牙权限，为您识别您周围的门锁设备。"
    )

     private fun createRequestListener(title: String, message: String): IPermissionPopupListener {
        return object : IPermissionPopupListener {
            private val utils by lazy {
                PkToastUtils.build(activity).apply {
                    setTitle(title)
                        .setMessage(message)
                }
            }

            override fun onShowPermissionPopup() {
                utils
                    .show()
            }

            override fun onHidePermissionPopup() {
                utils.dismiss()
            }

        }
    }

}
