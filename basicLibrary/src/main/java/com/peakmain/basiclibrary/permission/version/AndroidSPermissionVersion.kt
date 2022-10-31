package com.peakmain.basiclibrary.permission.version

import android.Manifest
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.permission.PkPermission
import com.peakmain.basiclibrary.permission.interfaces.IPermissionVersion

/**
 * author ：Peakmain
 * createTime：2022/10/31
 * mail:2726449200@qq.com
 * describe：android 12 权限处理
 */
class AndroidSPermissionVersion :
    IPermissionVersion {
    override fun permissionVersion(chain: IPermissionVersion.Chain): IPermissionVersion {
        val request = chain.request()
        val permissionList = request.permissionList
        if (AndroidVersion.isAndroid12()) {
            if (permissionList.contains(Manifest.permission.ACCESS_FINE_LOCATION) &&
                !permissionList.contains(Manifest.permission.ACCESS_COARSE_LOCATION) && !PkPermission.isGranted(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                //Android 12必须添加ACCESS_COARSE_LOCATION
                //官方适配文档：https://developer.android.google.cn/about/versions/12/approximate-location
                throw IllegalArgumentException(
                    "在android 12或更高的版本中，请勿单独请求ACCESS_FINE_LOCATION权限，" +
                            "而应在单个运行时请求中同时请求ACCESS_FINE_LOCATION和ACCESS_COARSE_LOCATION权限。"
                )
            }
        }
        return chain.proceed(request)
    }
}