package com.peakmain.basiclibrary.permission

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.helper.PermissionHelper
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback


/**
 * author ：Peakmain
 * createTime：2022/08/11
 * mail:2726449200@qq.com
 * describe：
 */
internal class PkPermissionFragment : Fragment() {
    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 123
    }

    private var mOnPermissionCallback: OnPermissionCallback? = null

    /**
     * 是否授予了某个权限
     */
    fun isGranted(permission: String): Boolean {
        return PermissionHelper.instance.isGranted(permission)
    }

    fun isGranted(permissions: Array<String>): Boolean {
        return PermissionHelper.instance.isGranted(permissions)
    }


    fun isRevoked(permission: String): Boolean {
        val fragmentActivity = activity ?: return false
        return !AndroidVersion.isAndroid6() || fragmentActivity.packageManager.isPermissionRevokedByPolicy(
            permission,
            activity!!.packageName
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    /**
     * 申请权限
     */
    fun requestPermissions(permissions: Array<String>, block: OnPermissionCallback) {
        this.mOnPermissionCallback = block
        val deniedPermissions = PermissionHelper.instance.getDeniedPermissions(*permissions)
        if (!AndroidVersion.isAndroid6()||deniedPermissions.isEmpty()) {
            mOnPermissionCallback?.onGranted(permissions)
        } else {
            requestPermissions(deniedPermissions.toTypedArray(), PERMISSIONS_REQUEST_CODE)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != PERMISSIONS_REQUEST_CODE) return
        onRequestPermissionsResult(permissions, grantResults)
    }

    fun onRequestPermissionsResult(
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (PermissionHelper.instance.isGranted(grantResults)) {
            //所有权限都同意了
            mOnPermissionCallback?.onGranted(permissions)
        } else {
            denyPermssionOnRequestPermissionResult(permissions, grantResults)
        }

    }

    private fun denyPermssionOnRequestPermissionResult(
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (!PermissionHelper.instance
                .shouldShowRequestPermissionRationale
                    (this, permissions)
        ) {
            //永久拒绝了权限
            if (permissions.size != grantResults.size) return
            val denyPermissions = ArrayList<String>()
            grantResults.forEachIndexed { index, grantedId ->
                if (grantedId == -1) {
                    denyPermissions.add(permissions[index])
                }
            }
            mOnPermissionCallback?.onDenied(
                permissions, true
            )
        } else {
            //权限取消
            mOnPermissionCallback?.onDenied(
                permissions, false
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mOnPermissionCallback != null) {
            mOnPermissionCallback = null
        }
    }

}