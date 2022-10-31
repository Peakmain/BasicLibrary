package com.peakmain.basiclibrary.permission.version

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.extend.launchMulti
import com.peakmain.basiclibrary.permission.interfaces.IPermissionVersion

/**
 * author ：Peakmain
 * createTime：2022/10/31
 * mail:2726449200@qq.com
 * describe：
 */
class AndroidOtherPermissionVersion(
    private val launcher: ActivityResultLauncher<Array<String>>,
    private val permissions: Array<String>,
    private val block: (() -> Unit)? = null
) :
    IPermissionVersion {
    override fun permissionVersion(chain: IPermissionVersion.Chain): IPermissionVersion? {
        val request = chain.request()
        val permissionList = request.permissionList
        if (!permissionList.contains(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
            launcher.launch(permissions)
            return this
        }
        if (permissionList.contains(Manifest.permission.ACCESS_COARSE_LOCATION)
            && !permissionList.contains(Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        //后台定位权限不要和其他权限一起申请
        for (permission in permissions) {
            if (permission != Manifest.permission.ACCESS_FINE_LOCATION || permission != Manifest.permission.ACCESS_COARSE_LOCATION || permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION) {
                continue
            }
            throw  IllegalArgumentException("因为有background location 权限, 请不要申请与位置无关的权限!!")
        }
        if (AndroidVersion.isAndroid10() && permissionList.size >= 2) {
            permissionList.remove(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            launcher.launchMulti(permissionList.toTypedArray())
            block?.invoke()
            return this
        }
        launcher.launch(permissions)
        return this
    }
}