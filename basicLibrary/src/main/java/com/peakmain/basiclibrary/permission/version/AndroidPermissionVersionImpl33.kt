package com.peakmain.basiclibrary.permission.version

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.manager.PermissionHandlerManager
import com.peakmain.basiclibrary.permission.interfaces.IPermissionVersion

/**
 * author ：Peakmain
 * createTime：2022/11/01
 * mail:2726449200@qq.com
 * describe：android 13权限
 */
class AndroidPermissionVersionImpl33(
    private val launcher: ActivityResultLauncher<Array<String>>,
    private val permissions: Array<String>
) :
    IPermissionVersion {
    override fun permissionVersion(chain: IPermissionVersion.Chain): IPermissionVersion? {
        val request = chain.request()
        if (AndroidVersion.isAndroid13()) {
            val permissionList = request.permissionList
            if (permissionList.contains(Manifest.permission.POST_NOTIFICATIONS)
                || permissionList.contains(Manifest.permission.READ_MEDIA_IMAGES)
                || permissionList.contains(Manifest.permission.READ_MEDIA_AUDIO)
                || permissionList.contains(Manifest.permission.READ_MEDIA_VIDEO)
                || permissionList.contains(Manifest.permission.NEARBY_WIFI_DEVICES)
            ) {
                PermissionHandlerManager.instance.sendMessage()
                launcher.launch(permissions)
                return this
            }
        }
        return chain.proceed(request)
    }
}