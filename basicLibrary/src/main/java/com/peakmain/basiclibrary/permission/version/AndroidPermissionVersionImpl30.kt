package com.peakmain.basiclibrary.permission.version

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.manager.PermissionHandlerManager
import com.peakmain.basiclibrary.permission.interfaces.IPermissionVersion

/**
 * author ：Peakmain
 * createTime：2022/10/31
 * mail:2726449200@qq.com
 * describe：android 11
 */
class AndroidPermissionVersionImpl30(
    private val launcher: ActivityResultLauncher<Array<String>>,
    private val permissions: Array<String>
) : IPermissionVersion {
    override fun permissionVersion(chain: IPermissionVersion.Chain): IPermissionVersion? {
        val request = chain.request()
        if (AndroidVersion.isAndroid11()) {
            val permissionList = request.permissionList
            if (permissionList.contains(Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
                PermissionHandlerManager.instance.sendMessage(permissionList.toTypedArray())
                launcher.launch(permissions)
                return this
            }
        }
        return chain.proceed(request)
    }
}