package com.peakmain.basiclibrary.permission.version

import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.permission.interfaces.IPermissionVersion

/**
 * author ：Peakmain
 * createTime：2022/10/31
 * mail:2726449200@qq.com
 * describe：android 11
 */
class AndroidRPermissionVersion : IPermissionVersion {
    override fun permissionVersion(chain: IPermissionVersion.Chain): IPermissionVersion? {
        val request = chain.request()
        if (AndroidVersion.isAndroid11()) {

        }
        return chain.proceed(request)
    }
}