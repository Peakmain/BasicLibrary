package com.peakmain.basiclibrary.permission.version

/**
 * author ：Peakmain
 * createTime：2022/10/31
 * mail:2726449200@qq.com
 * describe：
 */
class RealPermissionVersionChain(
    val permissionVersionList: MutableList<IPermissionVersion>,
    val index: Int,
    val request: PermissionRequest
) : IPermissionVersion.Chain {

    override fun request(): PermissionRequest {
        return request
    }

    override fun proceed(request: PermissionRequest): IPermissionVersion {
        if (index >= permissionVersionList.size) throw AssertionError()
        val realPermissionVersionChain =
            RealPermissionVersionChain(permissionVersionList,index + 1, request)
        val iPermissionVersion = permissionVersionList[index]
        val permissionVersion = iPermissionVersion.permissionVersion(realPermissionVersionChain)

        if (permissionVersion == null) {
            throw NullPointerException("permissionVersion $permissionVersion returned null")
        }
        return permissionVersion
    }
}