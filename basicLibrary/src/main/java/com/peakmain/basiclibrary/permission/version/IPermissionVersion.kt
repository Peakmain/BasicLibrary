package com.peakmain.basiclibrary.permission.version

/**
 * author ：Peakmain
 * createTime：2022/10/31
 * mail:2726449200@qq.com
 * describe：
 */
interface IPermissionVersion {
    fun permissionVersion(chain:Chain): IPermissionVersion?
    interface Chain {
        fun request(): PermissionRequest
        fun proceed(request: PermissionRequest): IPermissionVersion
    }
}