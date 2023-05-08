package com.peakmain.basiclibrary.permission.version

import com.peakmain.basiclibrary.permission.interfaces.IPermissionVersion

/**
 * author ：Peakmain
 * createTime：2022/10/31
 * mail:2726449200@qq.com
 * describe：
 */
class RealPermissionVersionChain(
    private val permissionVersionList: MutableList<IPermissionVersion>,
    private val index: Int,
    val request: PermissionRequest
) : IPermissionVersion.Chain {

    override fun request(): PermissionRequest {
        return request
    }

    override fun proceed(request: PermissionRequest): IPermissionVersion {
        if (index >= permissionVersionList.size) throw AssertionError()
        val realPermissionVersionChain =
            RealPermissionVersionChain(permissionVersionList, index + 1, request)
        val iPermissionVersion = permissionVersionList[index]
        return iPermissionVersion.permissionVersion(realPermissionVersionChain)
            ?: throw NullPointerException("permissionVersion returned null")
    }
}