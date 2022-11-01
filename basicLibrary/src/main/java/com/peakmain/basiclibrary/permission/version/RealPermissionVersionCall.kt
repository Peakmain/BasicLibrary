package com.peakmain.basiclibrary.permission.version

import androidx.activity.result.ActivityResultLauncher
import com.peakmain.basiclibrary.permission.interfaces.ICall
import com.peakmain.basiclibrary.permission.interfaces.IPermissionVersion

/**
 * author ：Peakmain
 * createTime：2022/10/31
 * mail:2726449200@qq.com
 * describe：
 */
class RealPermissionVersionCall(
    private val permissions: Array<String>,
    private val launcher: ActivityResultLauncher<Array<String>>,
    val block: (() -> Unit)? = null
) : ICall {

    override fun call() {
        val permissionVersionList = ArrayList<IPermissionVersion>()
        permissionVersionList.add(AndroidPermissionVersionImpl33(launcher, permissions))
        permissionVersionList.add(AndroidPermissionVersionImpl31(launcher, permissions))
        permissionVersionList.add(AndroidPermissionVersionImpl30(launcher, permissions))
        permissionVersionList.add(AndroidPermissionVersionImpl29())
        permissionVersionList.add(AndroidOtherPermissionVersion(launcher, permissions, block))
        val permissionRequest = PermissionRequest(permissions.toMutableList())
        val realPermissionVersionChain = RealPermissionVersionChain(
            permissionVersionList, 0,
            permissionRequest
        )
        realPermissionVersionChain.proceed(permissionRequest)
    }

    override fun clone(): RealPermissionVersionCall {
        return RealPermissionVersionCall(permissions, launcher, block)
    }
}