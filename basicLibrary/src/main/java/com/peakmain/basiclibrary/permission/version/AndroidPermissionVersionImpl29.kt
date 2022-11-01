package com.peakmain.basiclibrary.permission.version

import android.Manifest
import android.os.Build
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.permission.interfaces.IPermissionVersion

/**
 * author ：Peakmain
 * createTime：2022/10/31
 * mail:2726449200@qq.com
 * describe：android 10
 */
class AndroidPermissionVersionImpl29(
) : IPermissionVersion {
    override fun permissionVersion(chain: IPermissionVersion.Chain): IPermissionVersion? {
        val request = chain.request()
        if (Build.VERSION.SDK_INT == AndroidVersion.ANDROID_10) {
            val permissionList = request.permissionList
            if (permissionList.contains(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                && !Environment.isExternalStorageLegacy()
            ) {
                //有MANAGE_EXTERNAL_STORAGE权限，必须设置 android:requestLegacyExternalStorage="true"
                throw IllegalArgumentException("Application中必须设置android:requestLegacyExternalStorage=\"true\"")
            }
        }
        return chain.proceed(request)
    }
}